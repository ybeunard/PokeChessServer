package com.pokechess.server.services.party;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PartyService {
    private final UserRepository userRepository;
    private final PartyRepository partyRepository;
    private final PlayerRepository playerRepository;
    private final PasswordEncoder bcryptEncoder;

    public PartyService(UserRepository userRepository,
                        PartyRepository partyRepository,
                        PlayerRepository playerRepository,
                        PasswordEncoder bcryptEncoder) {
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
        return this.partyRepository.create(newParty.build());
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
}
