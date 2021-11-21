package com.pokechess.server.services.party;

import com.pokechess.server.exceptions.PartyException;
import com.pokechess.server.exceptions.UserException;
import com.pokechess.server.models.enumerations.PartyState;
import com.pokechess.server.models.globals.user.User;
import com.pokechess.server.models.party.BoardGame;
import com.pokechess.server.models.party.Party;
import com.pokechess.server.models.party.Player;
import com.pokechess.server.models.party.PokemonPlace;
import com.pokechess.server.repositories.party.PartyRepository;
import com.pokechess.server.repositories.player.PlayerRepository;
import com.pokechess.server.repositories.user.UserRepository;
import com.pokechess.server.services.security.SubscriptionRegistryService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.pokechess.server.services.security.SubscriptionRegistryService.*;

@Service
public class PartyService {
    private final SubscriptionRegistryService subscriptionRegistryService;
    private final UserRepository userRepository;
    private final PartyRepository partyRepository;
    private final PlayerRepository playerRepository;
    private final PasswordEncoder bcryptEncoder;

    public PartyService(SubscriptionRegistryService subscriptionRegistryService,
                        UserRepository userRepository,
                        PartyRepository partyRepository,
                        PlayerRepository playerRepository,
                        PasswordEncoder bcryptEncoder) {
        this.subscriptionRegistryService = subscriptionRegistryService;
        this.userRepository = userRepository;
        this.partyRepository = partyRepository;
        this.playerRepository = playerRepository;
        this.bcryptEncoder = bcryptEncoder;
    }

    /**
     *
     * @throws UserException User not found exception
     * @throws UserException User already in game exception, User cannot create a party if already in game
     */
    public Party createParty(String ownerUsername, String name, String password) {
        User owner = this.userRepository.getByUsername(ownerUsername);
        List<Player> players = new ArrayList<>();
        players.add(createNewPlayer(owner));

        Party.PartyBuilder newParty = Party.builder()
                .owner(owner).name(name).players(players).state(PartyState.CREATION);
        if (Objects.nonNull(password)) {
            newParty.password(bcryptEncoder.encode(password));
        }

        Party partyCreated = this.partyRepository.create(newParty.build());
        this.subscriptionRegistryService.removeSubscription(ownerUsername, PARTY_CREATION_BROKER_DESTINATION);
        this.subscriptionRegistryService.removeSubscription(ownerUsername, PARTY_UPDATE_PLAYER_NUMBER_BROKER_DESTINATION);
        this.subscriptionRegistryService.removeSubscription(ownerUsername, PARTY_DELETED_BROKER_DESTINATION);
        this.partyRepository.sendPartyCreationMessage(partyCreated);
        return partyCreated;
    }

    public void leaveParty(String playerUsername) {
        try {
            Party playerParty = this.partyRepository.getByPlayerNameAndState(playerUsername, PartyState.CREATION);
            if (playerParty.getOwner().getUsername().equals(playerUsername)) {
                this.partyRepository.deletePartyById(playerParty.getId());
                playerParty.setState(PartyState.DELETED);
                this.partyRepository.sendPartyChangeStateMessage(playerParty);
                this.partyRepository.sendPartyDeletedMessage(playerParty.getName());
                playerParty.getPlayers().forEach(player -> this.deletePlayerSubscription(playerParty, player));
            } else {
                playerParty.getPlayers().stream().filter(player -> player.getUser().getUsername().equals(playerUsername))
                        .forEach(player -> {
                            this.playerRepository.deletePlayerById(player.getId());
                            playerParty.getPlayers().remove(player);
                            this.deletePlayerSubscription(playerParty, player);
                        });
                this.partyRepository.sendPartyUpdatePlayerMessage(playerParty);
                this.partyRepository.sendPartyUpdatePlayerNumberMessage(playerParty);
            }
        } catch (PartyException ignored) { }
    }

    /**
     *
     * @throws UserException User already in game exception, User cannot join a party if already in game
     */
    private Player createNewPlayer(User user) {
        if (this.playerRepository.existsPlayerByUsername(user.getUsername())) {
            throw UserException.of(UserException.UserExceptionType.USER_ALREADY_IN_GAME);
        }
        return Player.builder().user(user).boardGame(createNewBoardGame()).build();
    }

    private BoardGame createNewBoardGame() {
        return BoardGame.builder()
                .offensiveLine(generatePokemonPlaceList()).defensiveLine(generatePokemonPlaceList())
                .bench(generatePokemonPlaceList()).pokemonCenter(PokemonPlace.builder().build()).build();
    }

    private List<PokemonPlace> generatePokemonPlaceList() {
        return Stream.generate(() -> PokemonPlace.builder().build()).limit(BoardGame.POKEMON_PLACE_LIST_LENGTH)
                .collect(Collectors.toList());
    }

    private void deletePlayerSubscription(Party party, Player player) {
        this.subscriptionRegistryService
                .removeSubscription(player.getUser().getUsername(), String.format(SPECIFIC_PARTY_UPDATE_PLAYER_BROKER_DESTINATION, party.getName()));
        this.subscriptionRegistryService
                .removeSubscription(player.getUser().getUsername(), String.format(SPECIFIC_PARTY_UPDATE_STATE_BROKER_DESTINATION, party.getName()));
    }
}
