package com.pokechess.server.datasources.database.player.mapper;

import com.pokechess.server.datasources.database.board.game.mapper.BoardGameEntityMapper;
import com.pokechess.server.datasources.database.player.entity.PlayerEntity;
import com.pokechess.server.datasources.database.user.mapper.UserEntityMapper;
import com.pokechess.server.models.party.Player;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerEntityMapper {
    public static List<Player> mapPlayerListFromPlayerEntityListWithoutGameObject(List<PlayerEntity> entityList) {
        return entityList.stream().map(PlayerEntityMapper::mapPlayerFromPlayerEntityWithoutGameObject)
                .collect(Collectors.toList());
    }

    private static Player mapPlayerFromPlayerEntityWithoutGameObject(PlayerEntity entity) {
        return Player.builder().id(entity.getId())
                .user(UserEntityMapper.mapUserFromUserEntity(entity.getUser()))
                .boardGame(BoardGameEntityMapper.mapBoardGameToBoardGameEntityWithoutGameObject(entity.getBoardGame()))
                .level(entity.getLevel()).experiencePoint(entity.getExperiencePoint())
                .lifePoint(entity.getLifePoint()).winCounter(entity.getWinCounter())
                .money(entity.getMoney()).build();
    }

    public static List<PlayerEntity> mapPlayerListToPlayerEntityList(List<Player> modelList) {
        return modelList.stream().map(PlayerEntityMapper::mapPlayerToPlayerEntity)
                .collect(Collectors.toList());
    }

    private static PlayerEntity mapPlayerToPlayerEntity(Player model) {
        PlayerEntity entity = new PlayerEntity();
        entity.setId(model.getId());
        entity.setUser(UserEntityMapper.mapUserToUserEntity(model.getUser()));
        entity.setBoardGame(BoardGameEntityMapper.mapBoardGameToBoardGameEntity(model.getBoardGame()));
        entity.setLevel(model.getLevel());
        entity.setExperiencePoint(model.getExperiencePoint());
        entity.setLifePoint(model.getLifePoint());
        entity.setWinCounter(model.getWinCounter());
        entity.setMoney(model.getMoney());
        return entity;
    }
}
