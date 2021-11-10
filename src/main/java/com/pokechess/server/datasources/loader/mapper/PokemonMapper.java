package com.pokechess.server.datasources.loader.mapper;

import com.pokechess.server.datasources.loader.dto.PokemonDTO;
import com.pokechess.server.datasources.loader.dto.actions.*;
import com.pokechess.server.models.enumerations.Generation;
import com.pokechess.server.models.enumerations.PokemonStatus;
import com.pokechess.server.models.enumerations.Synergy;
import com.pokechess.server.models.enumerations.Type;
import com.pokechess.server.models.enumerations.actions.*;
import com.pokechess.server.models.globals.game.actions.*;
import com.pokechess.server.models.globals.game.cards.Pokemon;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PokemonMapper {

    public static List<Pokemon> mapPokemonListFromPokemonDTOList(List<PokemonDTO> dtoList) {
        return dtoList.stream().map(PokemonMapper::mapPokemonFromPokemonDTO).collect(Collectors.toList());
    }

    public static Pokemon mapPokemonFromPokemonDTO(PokemonDTO dto) {
        Pokemon.PokemonBuilder builder = Pokemon.builder().pokemonId(dto.getPokemonId())
                .generation(Generation.getEnum(dto.getGeneration())).level(dto.getLevel())
                .lifePoint(dto.getLifePoint()).baseSpeed(dto.getBaseSpeed())
                .size(dto.getSize()).weight(dto.getWeight()).type(Type.getEnum(dto.getType()))
                .type2(Type.getEnum(dto.getType2()));

        Optional.ofNullable(dto.getOffensiveAttack()).map(PokemonMapper::mapAttackFromAttackDTO)
                .ifPresent(builder::offensiveAttack);
        Optional.ofNullable(dto.getDefensiveAttack()).map(PokemonMapper::mapAttackFromAttackDTO)
                .ifPresent(builder::defensiveAttack);
        Optional.ofNullable(dto.getWeaknesses()).map(ActionMapper::mapTypeListFromStringList)
                .ifPresent(builder::weaknesses);
        Optional.ofNullable(dto.getResistances()).map(ActionMapper::mapTypeListFromStringList)
                .ifPresent(builder::resistances);
        Optional.ofNullable(dto.getEvolutions()).map(PokemonMapper::mapEvolutionListFromEvolutionDTOList)
                .ifPresent(builder::evolutions);

        return builder.build();
    }

    public static Attack mapAttackFromAttackDTO(AttackDTO dto) {
        Attack.AttackBuilder builder = Attack.builder().type(Type.getEnum(dto.getType()))
                .power(dto.getPower()).precision(dto.getPrecision());
        Optional.ofNullable(dto.getEffects()).map(ActionMapper::mapEffectListFromEffectDTOList)
                .ifPresent(builder::effects);
        return builder.build();
    }

    public static List<Evolution> mapEvolutionListFromEvolutionDTOList(List<EvolutionDTO> dtoList) {
        return dtoList.stream().map(PokemonMapper::mapEvolutionFromEvolutionDTO).collect(Collectors.toList());
    }

    public static Evolution mapEvolutionFromEvolutionDTO(EvolutionDTO dto) {
        Evolution.EvolutionBuilder builder = Evolution.builder();
        Optional.ofNullable(dto.getPokemon()).map(PokemonMapper::mapPokemonFromPokemonDTO)
                .ifPresent(builder::pokemon);
        Optional.ofNullable(dto.getConditions()).map(ActionMapper::mapConditionListFromConditionDTOList)
                .ifPresent(builder::conditions);
        return builder.build();
    }
}
