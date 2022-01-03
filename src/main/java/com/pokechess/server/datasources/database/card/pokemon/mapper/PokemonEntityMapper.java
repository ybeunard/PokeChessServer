package com.pokechess.server.datasources.database.card.pokemon.mapper;

import com.pokechess.server.datasources.database.card.mapper.CardEntityMapper;
import com.pokechess.server.datasources.database.card.pokemon.entity.AttackEntity;
import com.pokechess.server.datasources.database.card.pokemon.entity.EvolutionEntity;
import com.pokechess.server.datasources.database.card.pokemon.entity.PokemonEntity;
import com.pokechess.server.models.enumerations.Generation;
import com.pokechess.server.models.enumerations.Type;
import com.pokechess.server.models.enumerations.actions.Target;
import com.pokechess.server.models.globals.game.actions.Attack;
import com.pokechess.server.models.globals.game.actions.Evolution;
import com.pokechess.server.models.globals.game.cards.Pokemon;
import com.pokechess.server.models.globals.game.effects.Effect;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.pokechess.server.datasources.database.card.mapper.CardEntityMapper.mapObjectListFromString;

public class PokemonEntityMapper {
    public static List<PokemonEntity> mapPokemonListToPokemonEntityList(List<Pokemon> modelList) {
        return modelList.stream().map(PokemonEntityMapper::mapPokemonToPokemonEntity)
                .collect(Collectors.toList());
    }

    private static PokemonEntity mapPokemonToPokemonEntity(Pokemon model) {
        PokemonEntity entity = new PokemonEntity();
        entity.setPokemonId(model.getPokemonId());
        entity.setName(model.getName());
        entity.setGeneration(model.getGeneration().name());
        entity.setLevel(model.getLevel());
        entity.setLifePoint(model.getLifePoint());
        entity.setBaseSpeed(model.getBaseSpeed());
        entity.setSize(model.getSize());
        entity.setWeight(model.getWeight());
        entity.setType(model.getType().name());
        entity.setType2(model.getType2().name());
        entity.setOffensiveAttack(mapAttackToAttackEntity(model.getOffensiveAttack()));
        entity.setDefensiveAttack(mapAttackToAttackEntity(model.getDefensiveAttack()));
        entity.setEvolutions(mapEvolutionListToEvolutionEntityList(model.getEvolutions()));
        return entity;
    }

    private static AttackEntity mapAttackToAttackEntity(Attack model) {
        AttackEntity entity = new AttackEntity();
        entity.setName(model.getName());
        entity.setDescription(model.getDescription());
        entity.setType(model.getType().name());
        entity.setTargets(CardEntityMapper.mapObjectToString(model.getTargets()));
        entity.setPower(model.getPower());
        entity.setPrecision(model.getPrecision());
        entity.setPriority(model.getPriority());
        entity.setEffects(model.getEffects().stream().map(Effect::mapToEntity).collect(Collectors.toList()));
        return entity;
    }

    private static List<EvolutionEntity> mapEvolutionListToEvolutionEntityList(List<Evolution> modelList) {
        return modelList.stream().map(PokemonEntityMapper::mapEvolutionToEvolutionEntity)
                .collect(Collectors.toList());
    }

    private static EvolutionEntity mapEvolutionToEvolutionEntity(Evolution model) {
        EvolutionEntity entity = new EvolutionEntity();
        entity.setPokemon(mapPokemonToPokemonEntity(model.getPokemon()));
        entity.setDescription(model.getDescription());
        entity.setConditions(CardEntityMapper.mapConditionListToConditionEntityList(model.getConditions()));
        return entity;
    }

    public static Pokemon mapPokemonFromPokemonEntity(PokemonEntity entity) {
        return Pokemon.builder().pokemonId(entity.getPokemonId()).name(entity.getName())
                .generation(Generation.getEnum(entity.getGeneration())).level(entity.getLevel())
                .lifePoint(entity.getLifePoint()).baseSpeed(entity.getBaseSpeed())
                .size(entity.getSize()).weight(entity.getWeight()).type(Type.getEnum(entity.getType()))
                .type2(Type.getEnum(entity.getType2())).offensiveAttack(mapAttackFromAttackEntity(entity.getOffensiveAttack()))
                .defensiveAttack(mapAttackFromAttackEntity(entity.getDefensiveAttack()))
                .evolutions(mapEvolutionListFromEvolutionEntityList(entity.getEvolutions())).build();
    }

    private static Attack mapAttackFromAttackEntity(AttackEntity entity) {
        return Attack.builder().name(entity.getName())
                .description(entity.getDescription()).type(Type.getEnum(entity.getType()))
                .power(entity.getPower()).precision(entity.getPrecision()).priority(entity.getPriority())
                .targets(mapObjectListFromString(entity.getTargets(), Target.class))
                .effects(CardEntityMapper.mapApplyWhenEffectListFromEffectEntityList(entity.getEffects())).build();
    }

    public static List<Evolution> mapEvolutionListFromEvolutionEntityList(List<EvolutionEntity> entityList) {
        if (Objects.isNull(entityList)) {
            return new ArrayList<>();
        }
        return entityList.stream().map(PokemonEntityMapper::mapEvolutionFromEvolutionEntity)
                .collect(Collectors.toList());
    }

    private static Evolution mapEvolutionFromEvolutionEntity(EvolutionEntity entity) {
        return Evolution.builder().description(entity.getDescription())
                .pokemon(mapPokemonFromPokemonEntity(entity.getPokemon()))
                .conditions(CardEntityMapper.mapConditionListFromConditionEntityList(entity.getConditions())).build();
    }
}
