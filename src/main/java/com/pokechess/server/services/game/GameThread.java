package com.pokechess.server.services.game;

import com.pokechess.server.exceptions.ApiException;
import com.pokechess.server.exceptions.GameException;
import com.pokechess.server.models.enumerations.PartyState;
import com.pokechess.server.models.globals.game.cards.Pokemon;
import com.pokechess.server.models.party.Party;
import com.pokechess.server.models.party.Player;
import com.pokechess.server.repositories.message.MessageRepository;
import com.pokechess.server.repositories.party.PartyRepository;
import com.pokechess.server.repositories.player.PlayerRepository;
import com.pokechess.server.services.security.SessionManagerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.pokechess.server.models.party.BoardGame.POKEMON_STARTING_POSITION;
import static com.pokechess.server.models.party.Party.*;
import static com.pokechess.server.models.party.Player.SERIES_GOLD;
import static com.pokechess.server.services.game.GameService.*;

public record GameThread(String partyName,
                         PartyRepository partyRepository,
                         PlayerRepository playerRepository,
                         MessageRepository messageRepository,
                         SessionManagerService sessionManagerService) {
    private static final Logger LOGGER = LogManager.getLogger(GameThread.class);

    private static final Integer STANDARD_WAITING_TIME = 500;
    private static final Integer CAROUSEL_ITERATION_NUMBER = 60; // CAROUSEL_PHASE_TIME = STANDARD_WAITING_TIME * CAROUSEL_ITERATION_NUMBER ms
    private static final Integer FIRST_TURN_PHASE_TIME = 15000;
    private static final Integer ACTION_PHASE_TIME = 60000;

    public void start() {
        try {
            synchronized (this) {
                this.partyLoaded();
                this.firstCarousel();
                this.firstTurn();
                this.fight();
                while (true) {
                    Party party = this.partyRepository.getPartyWithoutGameObjectByName(partyName);
                    if (party.getPlayers().stream().anyMatch(player -> player.getLevel() > 9)) {
                        this.sessionManagerService.terminateParty(partyName);
                        break;
                    }
                    this.runTurn();
                }
            }
        } catch (ApiException | InterruptedException e) {
            this.sessionManagerService.terminateParty(partyName);
            LOGGER.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    // Waiting all user ready
    private void partyLoaded() throws InterruptedException {
        Party party = this.partyRepository.getPartyWithoutGameObjectByName(this.partyName);
        while (!party.getPlayers().stream().allMatch(player -> !player.isLoading() || player.isDisconnected())) {
            wait(STANDARD_WAITING_TIME);
            party = this.partyRepository.getPartyWithoutGameObjectByName(this.partyName);
        }
    }

    private void firstCarousel() throws InterruptedException {
        Party party = this.partyRepository.changePartyState(this.partyName, PartyState.FIRST_CAROUSEL);
        this.messageRepository.sendPartyChangeStateMessage(party);

        // Draw phase
        this.partyPlayersDrawNewHand(party);

        // Wait all user
        for (int i = 0; i < CAROUSEL_ITERATION_NUMBER; i++) {
            wait(STANDARD_WAITING_TIME);
            party = this.partyRepository.getPartyByName(partyName);
            if (party.getPlayers().stream().allMatch(player -> player.getHand().isEmpty())) {
                break;
            }
        }

        party = this.partyRepository.changePartyState(this.partyName, PartyState.FIRST_CAROUSEL_END);
        this.messageRepository.sendPartyChangeStateMessage(party);

        // If player not select pokemon before end time, you select the first for him
        party = this.partyRepository.getPartyByName(partyName);
        party.getPlayers().stream().filter(player -> !player.getHand().isEmpty()).forEach(player -> {
            player.getBoardGame().getOffensiveLine()
                    .stream().filter(place -> Objects.isNull(place.getPokemon()) && place.getPosition().equals(POKEMON_STARTING_POSITION))
                    .findFirst().orElseThrow(() -> GameException.of(GameException.GameExceptionType.POKEMON_BENCH_PLACE_NOT_FOUND))
                    .setPokemon(createNewPokemonInstance(player.getHand().get(0)));
            player.setHand(new ArrayList<>());
            this.playerRepository.setPlayerOffensiveLine(player.getId(), player.getBoardGame().getOffensiveLine());
            this.playerRepository.setPlayerHand(player.getId(), player.getHand());
            this.messageRepository.sendPlayerHandMessage(player);
            this.messageRepository.sendPartyUpdatePlayerOffensiveMessage(partyName, player);
        });
    }

    private void firstTurn() throws InterruptedException {
        Party party = this.partyRepository.changePartyState(this.partyName, PartyState.FIRST_TURN);
        this.messageRepository.sendPartyChangeStateMessage(party);
        wait(FIRST_TURN_PHASE_TIME);
        party = this.partyRepository.changePartyState(this.partyName, PartyState.FIRST_TURN_END);
        this.messageRepository.sendPartyChangeStateMessage(party);
    }

    private void runTurn() throws InterruptedException {
        Party party = this.partyRepository.changePartyState(this.partyName, PartyState.TURN_PREPARATION_PHASE);
        this.messageRepository.sendPartyChangeStateMessage(party);
        party = this.partyRepository.getPartyByName(partyName);

        // Increment turn
        this.partyRepository.incrementPartyCurrentTurnNumber(party.getId());
        party.setCurrentTurnNumber(party.getCurrentTurnNumber() + 1);
        this.messageRepository.sendPartyChangeTurnMessage(party);

        party.getPlayers().forEach(player -> {
            // Increment experience
            player.setExperiencePoint(player.getExperiencePoint() + 1);
            Integer level = calculateTrainerLevel(player.getExperiencePoint());
            this.playerRepository.setPlayerExperience(player.getId(), level, player.getExperiencePoint());
            this.messageRepository.sendPlayerExperienceMessage(player);
            if (!player.getLevel().equals(level)) {
                player.setLevel(level);
                this.messageRepository.sendPartyUpdatePlayerLevelMessage(partyName, player);
            }

            // Update pokemon center
            if (Objects.nonNull(player.getBoardGame().getPokemonCenter().getPokemon())) {
                // TODO Heal the pokemon in the center
                this.playerRepository.setPlayerPokemonCenter(player.getId(), player.getBoardGame().getPokemonCenter());
                this.messageRepository.sendPartyUpdatePlayerPokemonCenterMessage(partyName, player);
            }

            // Add gold
            Integer goldAmount = this.calculateGold(player);
            this.playerRepository.setPlayerMoney(player.getId(), goldAmount);
            if (!calculatePlayerInterest(player.getMoney()).equals(calculatePlayerInterest(goldAmount))) {
                player.setMoney(goldAmount);
                this.messageRepository.sendPlayerGoldLevelMessage(partyName, player);
            }
            player.setMoney(goldAmount);
            this.messageRepository.sendPlayerGoldMessage(player);
        });

        // TODO Carousel

        // Draw phase
        this.partyPlayersDrawNewHand(party);

        // Unlock all hand
        party.getPlayers().forEach(player -> {
            if (!player.isLock()) return;
            this.playerRepository.setPlayerHandLock(player.getId(), false);
            player.setLock(false);
            this.messageRepository.sendPlayerHandLockMessage(player);
        });

        // Action phase
        party = this.partyRepository.changePartyState(this.partyName, PartyState.TURN_ACTION_PHASE);
        this.messageRepository.sendPartyChangeStateMessage(party);
        wait(ACTION_PHASE_TIME);
        party = this.partyRepository.changePartyState(this.partyName, PartyState.TURN_ACTION_PHASE_END);
        this.messageRepository.sendPartyChangeStateMessage(party);

        // Fight phase
        this.fight();
    }

    private void fight() {
        // Clean overload bench
        Party party = this.partyRepository.getPartyByName(partyName);
        party.getPlayers().forEach(player -> {
            if (!player.getBoardGame().getBenchOverload().isEmpty()) {
                Integer totalCoast = player.getBoardGame().getBenchOverload().stream()
                        .filter(pokemon -> Objects.nonNull(pokemon.getPokemon()))
                        .map(pokemon -> calculatePokemonCoast(pokemon.getPokemon().getPokemon()))
                        .reduce(NO_COAST, Integer::sum);
                this.playerRepository.resetPlayerBenchOverload(player.getId(), totalCoast);
                player.setMoney(player.getMoney() + totalCoast);
                player.getBoardGame().setBenchOverload(new ArrayList<>());
                this.messageRepository.sendPlayerGoldMessage(player);
                this.messageRepository.sendPartyUpdatePlayerBenchMessage(partyName, player);
            }
        });

        party = this.partyRepository.changePartyState(this.partyName, PartyState.TURN_FIGHT_PHASE);
        this.messageRepository.sendPartyChangeStateMessage(party);
        // TODO Fight
        party = this.partyRepository.changePartyState(this.partyName, PartyState.TURN_FIGHT_PHASE_END);
        this.messageRepository.sendPartyChangeStateMessage(party);
    }

    private void partyPlayersDrawNewHand(Party party) {
        party.getPlayers().forEach(player -> {
            if (player.isLock() && player.getLevel() > 2) return;
            List<Pokemon> playerHand = this.partyRepository.drawPokemon(this.partyName, player.getId(), TRAINER_HAND_SIZE, NO_COAST);
            this.playerRepository.setPlayerHand(player.getId(), playerHand);
            player.setHand(playerHand);
            this.messageRepository.sendPlayerHandMessage(player);
        });
    }

    private Integer calculateGold(Player player) {
        int playerSeries = SERIES_GOLD.get(Math.min(Math.abs(player.getWinCounter()), SERIES_GOLD.size() - 1));
        return player.getMoney() + player.getLevel() + playerSeries + calculatePlayerInterest(player.getMoney());
    }

    public static Integer calculatePlayerInterest(Integer money) {
        return Math.min(money / INTEREST_LEVEL, MAX_INTEREST);
    }
}
