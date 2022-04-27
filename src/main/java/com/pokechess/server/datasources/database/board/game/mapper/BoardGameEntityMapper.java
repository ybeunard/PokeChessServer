package com.pokechess.server.datasources.database.board.game.mapper;

import com.pokechess.server.datasources.database.board.game.entity.BoardGameEntity;
import com.pokechess.server.datasources.database.board.game.entity.PokemonInstanceEntity;
import com.pokechess.server.datasources.database.board.game.entity.PokemonPlaceEntity;
import com.pokechess.server.datasources.database.card.pokemon.mapper.PokemonEntityMapper;
import com.pokechess.server.models.party.BoardGame;
import com.pokechess.server.models.party.PokemonPlace;
import com.pokechess.server.models.party.instances.PokemonInstance;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.pokechess.server.datasources.database.card.pokemon.mapper.PokemonEntityMapper.mapPokemonToPokemonEntity;

public class BoardGameEntityMapper {
    public static BoardGame mapBoardGameToBoardGameEntityWithoutGameObject(BoardGameEntity entity) {
        return BoardGame.builder().id(entity.getId())
                .offensiveLine(mapPokemonPlaceListFromPokemonPlaceEntityListWithoutPokemon(entity.getOffensiveLine()))
                .defensiveLine(mapPokemonPlaceListFromPokemonPlaceEntityListWithoutPokemon(entity.getDefensiveLine()))
                .bench(mapPokemonPlaceListFromPokemonPlaceEntityListWithoutPokemon(entity.getBench()))
                .benchOverload(mapPokemonPlaceListFromPokemonPlaceEntityListWithoutPokemon(entity.getBenchOverload()))
                .pokemonCenter(mapPokemonPlaceFromPokemonPlaceEntityWithoutPokemon(entity.getPokemonCenter())).build();
    }

    public static BoardGame mapBoardGameToBoardGameEntity(BoardGameEntity entity) {
        return BoardGame.builder().id(entity.getId())
                .offensiveLine(mapPokemonPlaceListFromPokemonPlaceEntityList(entity.getOffensiveLine()))
                .defensiveLine(mapPokemonPlaceListFromPokemonPlaceEntityList(entity.getDefensiveLine()))
                .bench(mapPokemonPlaceListFromPokemonPlaceEntityList(entity.getBench()))
                .benchOverload(mapPokemonPlaceListFromPokemonPlaceEntityList(entity.getBenchOverload()))
                .pokemonCenter(mapPokemonPlaceFromPokemonPlaceEntity(entity.getPokemonCenter())).build();
    }

    private static List<PokemonPlace> mapPokemonPlaceListFromPokemonPlaceEntityListWithoutPokemon(List<PokemonPlaceEntity> entityList) {
        return entityList.stream().map(BoardGameEntityMapper::mapPokemonPlaceFromPokemonPlaceEntityWithoutPokemon)
                .collect(Collectors.toList());
    }

    private static List<PokemonPlace> mapPokemonPlaceListFromPokemonPlaceEntityList(List<PokemonPlaceEntity> entityList) {
        return entityList.stream().map(BoardGameEntityMapper::mapPokemonPlaceFromPokemonPlaceEntity)
                .collect(Collectors.toList());
    }

    private static PokemonPlace mapPokemonPlaceFromPokemonPlaceEntity(PokemonPlaceEntity entity) {
        PokemonPlace.PokemonPlaceBuilder builder = PokemonPlace.builder().id(entity.getId())
                .position(entity.getPosition());
        Optional.ofNullable(entity.getPokemonInstance())
                .map(BoardGameEntityMapper::mapPokemonInstanceFromPokemonInstanceEntity)
                .ifPresent(builder::pokemon);
        return builder.build();
    }

    private static PokemonPlace mapPokemonPlaceFromPokemonPlaceEntityWithoutPokemon(PokemonPlaceEntity entity) {
        return PokemonPlace.builder().id(entity.getId()).position(entity.getPosition()).build();
    }

    private static PokemonInstance mapPokemonInstanceFromPokemonInstanceEntity(PokemonInstanceEntity entity) {
        return PokemonInstance.builder().id(entity.getId())
                .pokemon(PokemonEntityMapper.mapPokemonFromPokemonEntity(entity.getPokemon())).build();
    }

    public static BoardGameEntity mapBoardGameToBoardGameEntity(BoardGame model) {
        BoardGameEntity entity = new BoardGameEntity();
        entity.setId(model.getId());
        entity.setOffensiveLine(mapPokemonPlaceListToPokemonPlaceEntityList(model.getOffensiveLine()));
        entity.setDefensiveLine(mapPokemonPlaceListToPokemonPlaceEntityList(model.getDefensiveLine()));
        entity.setBench(mapPokemonPlaceListToPokemonPlaceEntityList(model.getBench()));
        entity.setBenchOverload(mapPokemonPlaceListToPokemonPlaceEntityList(model.getBenchOverload()));
        entity.setPokemonCenter(mapPokemonPlaceToPokemonPlaceEntity(model.getPokemonCenter()));
        return entity;
    }

    public static List<PokemonPlaceEntity> mapPokemonPlaceListToPokemonPlaceEntityList(List<PokemonPlace> modelList) {
        return modelList.stream().map(BoardGameEntityMapper::mapPokemonPlaceToPokemonPlaceEntity)
                .collect(Collectors.toList());
    }

    public static PokemonPlaceEntity mapPokemonPlaceToPokemonPlaceEntity(PokemonPlace model) {
        PokemonPlaceEntity entity = new PokemonPlaceEntity();
        entity.setId(model.getId());
        entity.setPosition(model.getPosition());
        Optional.ofNullable(model.getPokemon())
                .map(BoardGameEntityMapper::mapPokemonInstanceToPokemonInstanceEntity)
                .ifPresent(entity::setPokemonInstance);
        return entity;
    }

    public static PokemonInstanceEntity mapPokemonInstanceToPokemonInstanceEntity(PokemonInstance model) {
        PokemonInstanceEntity entity = new PokemonInstanceEntity();
        entity.setId(model.getId());
        entity.setPokemon(mapPokemonToPokemonEntity(model.getPokemon()));
        return entity;
    }
}
