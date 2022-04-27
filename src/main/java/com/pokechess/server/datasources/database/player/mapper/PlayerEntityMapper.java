package com.pokechess.server.datasources.database.player.mapper;

import com.pokechess.server.datasources.database.board.game.mapper.BoardGameEntityMapper;
import com.pokechess.server.datasources.database.card.pokemon.mapper.PokemonEntityMapper;
import com.pokechess.server.datasources.database.player.entity.PlayerEntity;
import com.pokechess.server.datasources.database.user.mapper.UserEntityMapper;
import com.pokechess.server.models.party.Player;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PlayerEntityMapper {
    public static List<Player> mapPlayerListFromPlayerEntityListWithoutGameObject(List<PlayerEntity> entityList) {
        return entityList.stream().map(PlayerEntityMapper::mapPlayerFromPlayerEntityWithoutGameObject)
                .collect(Collectors.toList());
    }

    public static List<Player> mapPlayerListFromPlayerEntityList(List<PlayerEntity> entityList) {
        return entityList.stream().map(PlayerEntityMapper::mapPlayerFromPlayerEntity)
                .collect(Collectors.toList());
    }

    public static Player mapPlayerFromPlayerEntityWithoutGameObject(PlayerEntity entity) {
       return Player.builder().id(entity.getId())
                .user(UserEntityMapper.mapUserFromUserEntity(entity.getUser()))
                .boardGame(BoardGameEntityMapper.mapBoardGameToBoardGameEntityWithoutGameObject(entity.getBoardGame()))
                .level(entity.getLevel()).experiencePoint(entity.getExperiencePoint())
                .lifePoint(entity.getLifePoint()).winCounter(entity.getWinCounter()).lock(entity.isLock())
                .money(entity.getMoney()).loading(entity.isLoading()).disconnected(entity.isDisconnected()).build();
    }

    public static Player mapPlayerFromPlayerEntity(PlayerEntity entity) {
        Player.PlayerBuilder builder = Player.builder().id(entity.getId())
                .user(UserEntityMapper.mapUserFromUserEntity(entity.getUser()))
                .boardGame(BoardGameEntityMapper.mapBoardGameToBoardGameEntity(entity.getBoardGame()))
                .level(entity.getLevel()).experiencePoint(entity.getExperiencePoint())
                .lifePoint(entity.getLifePoint()).winCounter(entity.getWinCounter()).lock(entity.isLock())
                .money(entity.getMoney()).loading(entity.isLoading()).disconnected(entity.isDisconnected());
        Optional.ofNullable(entity.getHand())
                .map(PokemonEntityMapper::mapPokemonListFromPokemonEntityList).ifPresent(builder::hand);
        return builder.build();
    }

    public static List<PlayerEntity> mapPlayerListToPlayerEntityList(List<Player> modelList) {
        return modelList.stream().map(PlayerEntityMapper::mapPlayerToPlayerEntity)
                .collect(Collectors.toList());
    }

    public static PlayerEntity mapPlayerToPlayerEntity(Player model) {
        PlayerEntity entity = new PlayerEntity();
        entity.setId(model.getId());
        entity.setUser(UserEntityMapper.mapUserToUserEntity(model.getUser()));
        entity.setBoardGame(BoardGameEntityMapper.mapBoardGameToBoardGameEntity(model.getBoardGame()));
        entity.setLevel(model.getLevel());
        entity.setExperiencePoint(model.getExperiencePoint());
        entity.setLifePoint(model.getLifePoint());
        entity.setWinCounter(model.getWinCounter());
        entity.setLock(model.isLock());
        entity.setMoney(model.getMoney());
        entity.setLoading(model.isLoading());
        entity.setDisconnected(model.isDisconnected());
        return entity;
    }
}
