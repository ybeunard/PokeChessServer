package com.pokechess.server.datasources.loader.mapper;

import com.pokechess.server.datasources.loader.dto.PokemonLoaderDTO;
import com.pokechess.server.datasources.loader.dto.actions.AttackLoaderDTO;
import com.pokechess.server.datasources.loader.dto.actions.EvolutionLoaderDTO;
import com.pokechess.server.exceptions.loading.ActionException;
import com.pokechess.server.exceptions.loading.AttackException;
import com.pokechess.server.exceptions.loading.PokemonException;
import com.pokechess.server.models.enumerations.Generation;
import com.pokechess.server.models.enumerations.Type;
import com.pokechess.server.models.globals.game.actions.Attack;
import com.pokechess.server.models.globals.game.actions.Evolution;
import com.pokechess.server.models.globals.game.cards.Pokemon;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PokemonLoaderDTOMapper {

    public static List<Pokemon> mapPokemonListFromPokemonDTOList(List<PokemonLoaderDTO> dtoList) {
        return dtoList.stream().map(PokemonLoaderDTOMapper::mapPokemonFromPokemonDTO).collect(Collectors.toList());
    }

    public static Pokemon mapPokemonFromPokemonDTO(PokemonLoaderDTO dto) {
        try {
            Pokemon.PokemonBuilder builder = Pokemon.builder().pokemonId(dto.getPokemonId()).name(dto.getName())
                    .generation(Generation.getEnum(dto.getGeneration())).level(dto.getLevel())
                    .lifePoint(dto.getLifePoint()).baseSpeed(dto.getBaseSpeed())
                    .size(dto.getSize()).weight(dto.getWeight()).type(Type.getEnum(dto.getType()))
                    .type2(Type.getEnum(dto.getType2()));

            Optional.ofNullable(dto.getOffensiveAttack()).map(PokemonLoaderDTOMapper::mapAttackFromAttackDTO)
                    .ifPresent(builder::offensiveAttack);
            Optional.ofNullable(dto.getDefensiveAttack()).map(PokemonLoaderDTOMapper::mapAttackFromAttackDTO)
                    .ifPresent(builder::defensiveAttack);
            Optional.ofNullable(dto.getEvolutions()).map(PokemonLoaderDTOMapper::mapEvolutionListFromEvolutionDTOList)
                    .ifPresent(builder::evolutions);

            return builder.build();
        } catch (ActionException e) {
            throw PokemonException.of(dto.getPokemonId(), e);
        } catch (AttackException e) {
            throw PokemonException.of(dto.getPokemonId(), e);
        }
    }

    public static Attack mapAttackFromAttackDTO(AttackLoaderDTO dto) {
        Attack.AttackBuilder builder = Attack.builder().name(dto.getName())
                .description(dto.getDescription()).type(Type.getEnum(dto.getType()))
                .power(dto.getPower()).precision(dto.getPrecision()).priority(dto.getPriority())
                .targets(ActionLoaderMapper.mapTargetListFromStringList(dto.getTargets()));
        try {
            builder = builder.effects(ActionLoaderMapper.mapApplyWhenEffectListFromEffectLoaderDTOList(dto.getEffects()));
        } catch (ActionException e) {
            throw AttackException.of(dto.getName(), e);
        }
        return builder.build();
    }

    public static List<Evolution> mapEvolutionListFromEvolutionDTOList(List<EvolutionLoaderDTO> dtoList) {
        return dtoList.stream().map(PokemonLoaderDTOMapper::mapEvolutionFromEvolutionDTO).collect(Collectors.toList());
    }

    public static Evolution mapEvolutionFromEvolutionDTO(EvolutionLoaderDTO dto) {
        Evolution.EvolutionBuilder builder = Evolution.builder().description(dto.getDescription());
        try {
            Optional.ofNullable(dto.getPokemon()).map(PokemonLoaderDTOMapper::mapPokemonFromPokemonDTO)
                    .ifPresent(builder::pokemon);
            Optional.ofNullable(dto.getConditions()).map(ActionLoaderMapper::mapConditionListFromConditionLoaderDTOList)
                    .ifPresent(builder::conditions);
        } catch (ActionException e) {
            throw ActionException.of(ActionException.ActionExceptionType.EVOLUTION_VALIDATION, null, e);
        }
        return builder.build();
    }
}
