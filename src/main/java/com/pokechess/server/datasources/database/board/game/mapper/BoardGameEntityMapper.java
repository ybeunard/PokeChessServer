package com.pokechess.server.datasources.database.board.game.mapper;

import com.pokechess.server.datasources.database.board.game.entity.BoardGameEntity;
import com.pokechess.server.datasources.database.board.game.entity.PokemonPlaceEntity;
import com.pokechess.server.models.party.BoardGame;
import com.pokechess.server.models.party.PokemonPlace;

import java.util.List;
import java.util.stream.Collectors;

public class BoardGameEntityMapper {
    public static BoardGame mapBoardGameToBoardGameEntityWithoutGameObject(BoardGameEntity entity) {
        return BoardGame.builder().id(entity.getId())
                .offensiveLine(mapPokemonPlaceListFromPokemonPlaceEntityListWithoutPokemon(entity.getOffensiveLine()))
                .defensiveLine(mapPokemonPlaceListFromPokemonPlaceEntityListWithoutPokemon(entity.getDefensiveLine()))
                .bench(mapPokemonPlaceListFromPokemonPlaceEntityListWithoutPokemon(entity.getBench()))
                .benchOverload(mapPokemonPlaceListFromPokemonPlaceEntityListWithoutPokemon(entity.getBenchOverload()))
                .pokemonCenter(mapPokemonPlaceFromPokemonPlaceEntityWithoutPokemon(entity.getPokemonCenter()))
                .pokemonCenterCounter(entity.getPokemonCenterCounter()).build();
    }

    private static List<PokemonPlace> mapPokemonPlaceListFromPokemonPlaceEntityListWithoutPokemon(List<PokemonPlaceEntity> entityList) {
        return entityList.stream().map(BoardGameEntityMapper::mapPokemonPlaceFromPokemonPlaceEntityWithoutPokemon)
                .collect(Collectors.toList());
    }

    private static PokemonPlace mapPokemonPlaceFromPokemonPlaceEntityWithoutPokemon(PokemonPlaceEntity entity) {
        return PokemonPlace.builder().id(entity.getId()).build();
    }

    public static BoardGameEntity mapBoardGameToBoardGameEntity(BoardGame model) {
        BoardGameEntity entity = new BoardGameEntity();
        entity.setId(model.getId());
        entity.setOffensiveLine(mapPokemonPlaceListToPokemonPlaceEntityList(model.getOffensiveLine()));
        entity.setDefensiveLine(mapPokemonPlaceListToPokemonPlaceEntityList(model.getDefensiveLine()));
        entity.setBench(mapPokemonPlaceListToPokemonPlaceEntityList(model.getBench()));
        entity.setBenchOverload(mapPokemonPlaceListToPokemonPlaceEntityList(model.getBenchOverload()));
        entity.setPokemonCenter(mapPokemonPlaceToPokemonPlaceEntity(model.getPokemonCenter()));
        entity.setPokemonCenterCounter(model.getPokemonCenterCounter());
        return entity;
    }

    private static List<PokemonPlaceEntity> mapPokemonPlaceListToPokemonPlaceEntityList(List<PokemonPlace> modelList) {
        return modelList.stream().map(BoardGameEntityMapper::mapPokemonPlaceToPokemonPlaceEntity)
                .collect(Collectors.toList());
    }

    private static PokemonPlaceEntity mapPokemonPlaceToPokemonPlaceEntity(PokemonPlace model) {
        PokemonPlaceEntity entity = new PokemonPlaceEntity();
        entity.setId(model.getId());
        return entity;
    }
}
