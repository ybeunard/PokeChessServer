package com.pokechess.server.validators;

import com.pokechess.server.datasources.loader.dto.PokemonLoaderDTO;
import com.pokechess.server.datasources.loader.dto.actions.EvolutionLoaderDTO;
import com.pokechess.server.exceptions.ValidationException;
import com.pokechess.server.exceptions.loading.AttackException;
import com.pokechess.server.exceptions.loading.PokemonException;
import com.pokechess.server.models.enumerations.Type;
import com.pokechess.server.models.enumerations.actions.Target;
import com.pokechess.server.models.globals.game.actions.Attack;
import com.pokechess.server.models.globals.game.cards.Pokemon;
import com.pokechess.server.models.party.BoardGame;
import com.pokechess.server.models.party.PokemonPlace;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.pokechess.server.models.enumerations.Type.NO_TYPE;

public class PokemonValidator {
    private static final String POKEMON_VALIDATOR_NO_TYPE = "cannot be null or NO_TYPE";

    public static void validatePokemonDuplication(List<PokemonLoaderDTO> pokemonList) {
        List<DuplicatePokemonTest> duplicatePokemonTests = pokemonList.stream().map(pokemon -> {
            List<DuplicatePokemonTest> list = new ArrayList<>();
            list.add(new DuplicatePokemonTest(pokemon.getPokemonId(), pokemon.getName()));
            Optional.ofNullable(pokemon.getEvolutions())
                    .map(PokemonValidator::mapFromEvolution).ifPresent(list::addAll);
            return list;
        }).flatMap(List::stream).collect(Collectors.toList());

        if (duplicatePokemonTests.size() != duplicatePokemonTests.stream().distinct().count()) {
            throw PokemonException.of(PokemonException.PokemonExceptionType.POKEMON_DUPLICATION);
        }
    }

    public static void validate(Pokemon pokemon) {
        if (pokemon.getType().equals(pokemon.getType2())) {
            throw PokemonException.of(PokemonException.PokemonExceptionType.POKEMON_DUPLICATE_TYPE, pokemon.getPokemonId());
        }
        if (Objects.nonNull(pokemon.getDefensiveAttack().getPower())) {
            throw PokemonException.of(PokemonException.PokemonExceptionType.POKEMON_DEFENSIVE_ATTACK_POWER, pokemon.getPokemonId());
        }
    }

    public static void validate(Attack attack) {
        if (Objects.nonNull(attack.getPower()) && attack.getTargets().isEmpty()) {
            throw AttackException.of(AttackException.AttackExceptionType.ATTACK_NO_TARGET, attack.getName());
        }
        attack.getTargets().forEach(target -> {
            if (!Target.pokemonTargets.contains(target)) {
                throw AttackException.of(AttackException.AttackExceptionType.ATTACK_TARGET_POKEMON, attack.getName());
            }
        });
        attack.getEffects().forEach(effect -> {
            if (Objects.isNull(effect.getApplyWhen())) {
                throw AttackException.of(AttackException.AttackExceptionType.ATTACK_EFFECT_POKEMON, attack.getName(), effect.getEffectName());
            }
        });
    }

    public static void notNoType(Type type, String fieldName) {
        if (Objects.isNull(type) || NO_TYPE.equals(type)) {
            throw new ValidationException(fieldName, POKEMON_VALIDATOR_NO_TYPE);
        }
    }

    public static void correctPokemonPlaceListSize(List<PokemonPlace> pokemonPlaceSet) {
        if (Objects.isNull(pokemonPlaceSet) || BoardGame.POKEMON_PLACE_LIST_LENGTH.equals(pokemonPlaceSet.size())) {
            // TODO THROW
        }
    }

    private static List<DuplicatePokemonTest> mapFromEvolution(List<EvolutionLoaderDTO> evolutionList) {
        return evolutionList.stream().filter(evolution -> Objects.nonNull(evolution.getPokemon())).map(evolution -> {
            List<DuplicatePokemonTest> list = new ArrayList<>();
            list.add(new DuplicatePokemonTest(evolution.getPokemon().getPokemonId(), evolution.getPokemon().getName()));
            Optional.ofNullable(evolution.getPokemon().getEvolutions())
                    .map(PokemonValidator::mapFromEvolution).ifPresent(list::addAll);
            return list;
        }).flatMap(List::stream).collect(Collectors.toList());
    }

    private record DuplicatePokemonTest(String id, String name) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            DuplicatePokemonTest that = (DuplicatePokemonTest) o;
            if (Objects.equals(id, that.id)) return true;
            return Objects.equals(name, that.name);
        }
    }
}
