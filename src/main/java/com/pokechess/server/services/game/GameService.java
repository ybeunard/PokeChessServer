package com.pokechess.server.services.game;

import com.pokechess.server.exceptions.GameException;
import com.pokechess.server.exceptions.UserException;
import com.pokechess.server.models.enumerations.BoardPlace;
import com.pokechess.server.models.enumerations.PartyState;
import com.pokechess.server.models.globals.game.cards.Pokemon;
import com.pokechess.server.models.party.Party;
import com.pokechess.server.models.party.Player;
import com.pokechess.server.models.party.PokemonPlace;
import com.pokechess.server.models.party.instances.PokemonInstance;
import com.pokechess.server.repositories.message.MessageRepository;
import com.pokechess.server.repositories.party.PartyRepository;
import com.pokechess.server.repositories.player.PlayerRepository;
import com.pokechess.server.services.security.SessionManagerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.pokechess.server.models.party.BoardGame.*;
import static com.pokechess.server.models.party.Party.*;
import static com.pokechess.server.models.party.Player.LEVEL_STAGE;

@Service
public class GameService {
    private final PartyRepository partyRepository;
    private final PlayerRepository playerRepository;
    private final MessageRepository messageRepository;
    private final SessionManagerService sessionManagerService;

    public GameService(PartyRepository partyRepository, PlayerRepository playerRepository,
                       MessageRepository messageRepository, SessionManagerService sessionManagerService) {
        this.partyRepository = partyRepository;
        this.playerRepository = playerRepository;
        this.messageRepository = messageRepository;
        this.sessionManagerService = sessionManagerService;
    }

    public void start(String partyName, String username) {
        Party party = this.partyRepository.getPartyWithoutGameObjectByName(partyName);
        if (!party.getOwner().getUsername().equals(username) || !PartyState.CREATION.equals(party.getState())) return;

        this.partyRepository.startParty(partyName);
        party.setState(PartyState.STARTED);
        this.messageRepository.sendPartyChangeStateMessage(party);
        this.messageRepository.sendPartyDeletedMessage(party);
        GameThread game = new GameThread(partyName, partyRepository, playerRepository, messageRepository, sessionManagerService);
        new Thread(game::start).start();
    }

    public void hasLoadGame(String partyName, String username) {
        Party party = this.partyRepository.getPartyWithoutGameObjectByName(partyName);
        if (!PartyState.STARTED.equals(party.getState())
            && party.getPlayers().stream().anyMatch(player -> player.getUser().getUsername().equals(username))) return;

        this.playerRepository.hasLoadGamePlayer(username);
    }

    public void rollHand(String partyName, String username) {
        Party party = this.partyRepository.getPartyWithoutGameObjectByName(partyName);
        Player trainer = party.getPlayers().stream().filter(player -> player.getUser().getUsername().equals(username)).findFirst()
                .orElseThrow(() -> UserException.of(UserException.UserExceptionType.USER_NOT_FOUND));

        if (!PartyState.ROLL_POKEMON_PARTY_STATE.contains(party.getState())
                || trainer.getMoney() < ROLL_COAST) return;

        List<Pokemon> playerHand = this.partyRepository.drawPokemon(partyName, trainer.getId(), TRAINER_HAND_SIZE, ROLL_COAST);
        this.playerRepository.setPlayerHand(trainer.getId(), playerHand);
        trainer.setHand(playerHand);
        this.messageRepository.sendPlayerGoldMessage(trainer);
        this.messageRepository.sendPlayerHandMessage(trainer);
    }

    public void changeLockHand(String username) {
        Player player = this.playerRepository.getPlayerWithoutGameObjectByUsername(username);
        this.playerRepository.setPlayerHandLock(player.getId(), !player.isLock());
        player.setLock(!player.isLock());
        this.messageRepository.sendPlayerHandLockMessage(player);
    }

    public void buyExperience(String partyName, String username) {
        Party party = this.partyRepository.getPartyWithoutGameObjectByName(partyName);
        Player trainer = party.getPlayers().stream().filter(player -> player.getUser().getUsername().equals(username)).findFirst()
                .orElseThrow(() -> UserException.of(UserException.UserExceptionType.USER_NOT_FOUND));

        if (!PartyState.BUY_EXPERIENCE_PARTY_STATE.contains(party.getState())
                || trainer.getMoney() < EXPERIENCE_COAST) return;

        boolean levelUp = false;
        trainer.setExperiencePoint(trainer.getExperiencePoint() + EXPERIENCE_BY_PAY);
        Integer level = calculateTrainerLevel(trainer.getExperiencePoint());
        if (!trainer.getLevel().equals(level)) {
            trainer.setLevel(level);
            levelUp = true;
        }

        trainer = this.partyRepository.buyExperience(partyName, trainer.getId(),
                trainer.getExperiencePoint(), trainer.getLevel(), EXPERIENCE_COAST);
        this.messageRepository.sendPlayerExperienceMessage(trainer);
        this.messageRepository.sendPlayerGoldMessage(trainer);
        if (levelUp) this.messageRepository.sendPartyUpdatePlayerLevelMessage(partyName, trainer);
    }

    public void buyPokemonCard(String pokemonId, String partyName, String username) {
        Party party = this.partyRepository.getPartyByName(partyName);
        Player trainer = party.getPlayers().stream().filter(player -> player.getUser().getUsername().equals(username)).findFirst()
                .orElseThrow(() -> UserException.of(UserException.UserExceptionType.USER_NOT_FOUND));

        if (!PartyState.BUY_POKEMON_PARTY_STATE.contains(party.getState())
                || trainer.getBoardGame().getBench().stream()
                .noneMatch(place -> Objects.isNull(place.getPokemon()))) return;

        Pokemon pokemon = trainer.getHand().stream().filter(hand -> hand.getPokemonId().equals(pokemonId))
                .findFirst().orElseThrow(() -> GameException.of(GameException.GameExceptionType.POKEMON_HAND_NOT_FOUND));
        PokemonInstance pokemonInstance = createNewPokemonInstance(pokemon);
        if (PartyState.FIRST_CAROUSEL.equals(party.getState())) {
            trainer = this.partyRepository
                    .buyFirstPokemon(partyName, trainer.getId(), pokemonInstance);
            this.messageRepository.sendPartyUpdatePlayerOffensiveMessage(partyName, trainer);
        } else {
            Integer pokemonCoast = calculatePokemonCoast(pokemon);
            if (trainer.getMoney() < pokemonCoast) return;
            trainer = this.partyRepository.buyPokemon(partyName, trainer.getId(), pokemonCoast, pokemonInstance);
            this.messageRepository.sendPlayerGoldMessage(trainer);
            this.messageRepository.sendPartyUpdatePlayerBenchMessage(partyName, trainer);
        }
        this.messageRepository.sendPlayerHandMessage(trainer);
    }

    public void movePokemon(String partyName, String username, String pokemonId, BoardPlace place, Integer position,
                            BoardPlace destinationPlace, Integer destinationPosition) {
        Party party = this.partyRepository.getPartyByName(partyName);
        if (!PartyState.MOVE_POKEMON_PARTY_STATE.contains(party.getState())) return;

        Player trainer = party.getPlayers().stream().filter(player -> player.getUser().getUsername().equals(username)).findFirst()
                .orElseThrow(() -> UserException.of(UserException.UserExceptionType.USER_NOT_FOUND));
        if (trainer.getLevel() < LEVEL_LIMIT
                && (BoardPlace.BOARD_PLACES.contains(place) &&
                position < MIN_POKEMON_PLACE_BEFORE_LEVEL_LIMIT || position > MAX_POKEMON_PLACE_BEFORE_LEVEL_LIMIT)
                || (BoardPlace.BOARD_PLACES.contains(destinationPlace) &&
                destinationPosition < MIN_POKEMON_PLACE_BEFORE_LEVEL_LIMIT || destinationPosition > MAX_POKEMON_PLACE_BEFORE_LEVEL_LIMIT))
            return;

        // Verify Pokemon presence and verify not move on bench overload
        Optional<PokemonPlace> pokemonPlaceOpt = switch (place) {
            case OFFENSIVE -> getPlaceFromPokemonIdAndPosition(trainer.getBoardGame().getOffensiveLine(), pokemonId, position);
            case DEFENSIVE -> getPlaceFromPokemonIdAndPosition(trainer.getBoardGame().getDefensiveLine(), pokemonId, position);
            case BENCH -> getPlaceFromPokemonIdAndPosition(trainer.getBoardGame().getBench(), pokemonId, position);
            case BENCH_OVERLOAD -> {
                Optional<PokemonPlace> destination = switch (destinationPlace) {
                    case OFFENSIVE -> getPlaceFromPosition(trainer.getBoardGame().getOffensiveLine(), destinationPosition);
                    case DEFENSIVE -> getPlaceFromPosition(trainer.getBoardGame().getDefensiveLine(), destinationPosition);
                    case BENCH -> getPlaceFromPosition(trainer.getBoardGame().getBench(), destinationPosition);
                    case BENCH_OVERLOAD -> Optional.empty();
                    case CENTER -> Optional.of(trainer.getBoardGame().getPokemonCenter());
                };
                if (destination.filter(pokemonPlace -> Objects.isNull(pokemonPlace.getPokemon())).isEmpty()) yield Optional.empty();
                yield trainer.getBoardGame().getBenchOverload().stream()
                        .filter(pokemon -> Objects.nonNull(pokemon.getPokemon())
                                && pokemon.getPokemon().getPokemon().getPokemonId().equals(pokemonId)).findFirst();
            }
            case CENTER -> Optional.of(trainer.getBoardGame().getPokemonCenter())
                    .filter(pokemon -> Objects.nonNull(pokemon.getPokemon())
                            && pokemon.getPokemon().getPokemon().getPokemonId().equals(pokemonId));
        };
        if (pokemonPlaceOpt.isEmpty()) return;

        trainer = this.partyRepository.movePokemon(partyName, trainer.getId(), pokemonId, place, position,
                destinationPlace, destinationPosition);
        sendBoardMessageChange(partyName, trainer, place);
        if (!place.equalsWithBenchEquality(destinationPlace)) sendBoardMessageChange(partyName, trainer, destinationPlace);
    }

    public void sellPokemon(String partyName, String username, String pokemonId, BoardPlace place, Integer position) {
        Party party = this.partyRepository.getPartyByName(partyName);
        if (!PartyState.SELL_POKEMON_PARTY_STATE.contains(party.getState())) return;

        Player trainer = party.getPlayers().stream().filter(player -> player.getUser().getUsername().equals(username)).findFirst()
                .orElseThrow(() -> UserException.of(UserException.UserExceptionType.USER_NOT_FOUND));

        // Verify Pokemon presence
        Optional<PokemonPlace> pokemonPlaceOpt = switch (place) {
            case OFFENSIVE -> getPlaceFromPokemonIdAndPosition(trainer.getBoardGame().getOffensiveLine(), pokemonId, position);
            case DEFENSIVE -> getPlaceFromPokemonIdAndPosition(trainer.getBoardGame().getDefensiveLine(), pokemonId, position);
            case BENCH -> getPlaceFromPokemonIdAndPosition(trainer.getBoardGame().getBench(), pokemonId, position);
            case BENCH_OVERLOAD -> trainer.getBoardGame().getBenchOverload().stream()
                    .filter(pokemon -> Objects.nonNull(pokemon.getPokemon())
                            && pokemon.getPokemon().getPokemon().getPokemonId().equals(pokemonId)).findFirst();
            case CENTER -> Optional.of(trainer.getBoardGame().getPokemonCenter())
                    .filter(pokemon -> Objects.nonNull(pokemon.getPokemon())
                            && pokemon.getPokemon().getPokemon().getPokemonId().equals(pokemonId));
        };
        if (pokemonPlaceOpt.isEmpty()) return;

        PokemonInstance pokemon = pokemonPlaceOpt.get().getPokemon();
        if (Objects.isNull(pokemon)) return;

        trainer = this.partyRepository.sellPokemon(partyName, trainer.getId(), pokemonId, place, position,
                calculatePokemonCoast(pokemon.getPokemon()));

        switch (place) {
            case OFFENSIVE -> {
                this.messageRepository.sendPartyUpdatePlayerOffensiveMessage(partyName, trainer);
                this.messageRepository.sendPartyUpdatePlayerDefensiveMessage(partyName, trainer);
                this.messageRepository.sendPartyUpdatePlayerBenchMessage(partyName, trainer);
            }
            case DEFENSIVE -> {
                this.messageRepository.sendPartyUpdatePlayerDefensiveMessage(partyName, trainer);
                this.messageRepository.sendPartyUpdatePlayerBenchMessage(partyName, trainer);
            }
            case BENCH, BENCH_OVERLOAD -> this.messageRepository.sendPartyUpdatePlayerBenchMessage(partyName, trainer);
            case CENTER -> this.messageRepository.sendPartyUpdatePlayerPokemonCenterMessage(partyName, trainer);
        }
        this.messageRepository.sendPlayerGoldMessage(trainer);
    }

    public static PokemonInstance createNewPokemonInstance(Pokemon pokemon) {
        return PokemonInstance.builder().pokemon(pokemon).build();
    }

    public static Integer calculatePokemonCoast(Pokemon pokemon) {
        return pokemon.getLevel();
    }

    public static Integer calculateTrainerLevel(Integer experiencePoint) {
        for (int i = LEVEL_STAGE.size() - 1; i >= 0; i--) {
            if (LEVEL_STAGE.get(i) <= experiencePoint) {
                return i + 1;
            }
        }
        return 1;
    }

    private Optional<PokemonPlace> getPlaceFromPokemonIdAndPosition(List<PokemonPlace> places, String pokemonId, Integer position) {
        Optional<PokemonPlace> place = getPlaceFromPosition(places, position);
        if (place.isEmpty() || Optional.ofNullable(place.get().getPokemon())
                .filter(instance -> instance.getPokemon().getPokemonId().equals(pokemonId)).isEmpty())
            return Optional.empty();
        return place;
    }

    private Optional<PokemonPlace> getPlaceFromPosition(List<PokemonPlace> places, Integer position) {
        return places.stream().filter(place -> place.getPosition().equals(position))
                .findFirst();
    }

    private void sendBoardMessageChange(String partyName, Player player, BoardPlace place) {
        switch (place) {
            case OFFENSIVE, DEFENSIVE -> {
                this.messageRepository.sendPartyUpdatePlayerOffensiveMessage(partyName, player);
                this.messageRepository.sendPartyUpdatePlayerDefensiveMessage(partyName, player);
            }
            case BENCH, BENCH_OVERLOAD -> this.messageRepository.sendPartyUpdatePlayerBenchMessage(partyName, player);
            case CENTER -> this.messageRepository.sendPartyUpdatePlayerPokemonCenterMessage(partyName, player);
        }
    }
}
