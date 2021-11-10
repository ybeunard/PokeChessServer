package com.pokechess.server.validators;

import com.pokechess.server.models.enumerations.Type;
import com.pokechess.server.models.party.BoardGame;
import com.pokechess.server.models.party.PokemonPlace;

import java.util.List;
import java.util.Objects;

import static com.pokechess.server.models.enumerations.Type.NO_TYPE;

public class PokemonValidator {
    public static void notNoType(Type type) {
        if (Objects.isNull(type) || NO_TYPE.equals(type)) {
            // TODO THROW
        }
    }

    public static void notContainsNoType(List<Type> typeList) {
        typeList.forEach(PokemonValidator::notNoType);
    }

    public static void correctPokemonPlaceListSize(List<PokemonPlace> pokemonPlaceSet) {
        if (Objects.isNull(pokemonPlaceSet) || BoardGame.POKEMON_PLACE_LIST_LENGTH.equals(pokemonPlaceSet.size())) {
            // TODO THROW
        }
    }
}
