package com.pokechess.server.datasources.database.card.pokemon.mapper;

import com.pokechess.server.datasources.database.card.mapper.CardEntityMapper;
import com.pokechess.server.datasources.database.card.pokemon.entity.AttackEntity;
import com.pokechess.server.datasources.database.card.pokemon.entity.EvolutionEntity;
import com.pokechess.server.datasources.database.card.pokemon.entity.PokemonEntity;
import com.pokechess.server.models.globals.game.actions.Attack;
import com.pokechess.server.models.globals.game.actions.Evolution;
import com.pokechess.server.models.globals.game.cards.Pokemon;
import com.pokechess.server.models.globals.game.effects.Effect;

import java.util.List;
import java.util.stream.Collectors;

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
}
