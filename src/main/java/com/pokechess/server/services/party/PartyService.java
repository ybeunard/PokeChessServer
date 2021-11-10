package com.pokechess.server.services.party;

import com.pokechess.server.models.enumerations.PartyState;
import com.pokechess.server.models.globals.User;
import com.pokechess.server.models.party.BoardGame;
import com.pokechess.server.models.party.Party;
import com.pokechess.server.models.party.Player;
import com.pokechess.server.models.party.PokemonPlace;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PartyService {

    public Party createParty(User owner, String name) {
        List<Player> players = new ArrayList<>();
        players.add(createNewPlayer(owner));

        return Party.builder().id(UUID.randomUUID())
                .owner(owner).name(name).players(players).state(PartyState.CREATION).build();
    }

    private Player createNewPlayer(User user) {
        // Generate the board of the user
        BoardGame newBoard = BoardGame.builder()
                .offensiveLine(generatePokemonPlaceList()).defensiveLine(generatePokemonPlaceList())
                .bench(generatePokemonPlaceList()).pokemonCenter(PokemonPlace.builder().build()).build();
        return Player.builder().user(user).boardGame(newBoard).build();
    }

    private List<PokemonPlace> generatePokemonPlaceList() {
        return Stream.generate(() -> PokemonPlace.builder().build()).limit(BoardGame.POKEMON_PLACE_LIST_LENGTH)
                .collect(Collectors.toList());
    }
}
