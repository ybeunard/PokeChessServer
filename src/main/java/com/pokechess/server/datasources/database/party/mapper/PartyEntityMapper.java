package com.pokechess.server.datasources.database.party.mapper;

import com.pokechess.server.datasources.database.party.entity.PartyEntity;
import com.pokechess.server.datasources.database.player.mapper.PlayerEntityMapper;
import com.pokechess.server.datasources.database.user.mapper.UserEntityMapper;
import com.pokechess.server.models.enumerations.PartyState;
import com.pokechess.server.models.party.Party;

public class PartyEntityMapper {
    public static Party mapPartyFromPartyEntityWithoutGameObject(PartyEntity entity) {
        return Party.builder().id(entity.getId())
                .owner(UserEntityMapper.mapUserFromUserEntity(entity.getOwner()))
                .players(PlayerEntityMapper.mapPlayerListFromPlayerEntityListWithoutGameObject(entity.getPlayers()))
                .state(PartyState.getEnum(entity.getState()))
                .name(entity.getName()).password(entity.getPassword())
                .currentTurnNumber(entity.getCurrentTurnNumber()).build();
    }

    public static PartyEntity mapPartyToPartyEntity(Party model) {
        PartyEntity entity = new PartyEntity();
        entity.setId(model.getId());
        entity.setOwner(UserEntityMapper.mapUserToUserEntity(model.getOwner()));
        entity.setName(model.getName());
        entity.setPassword(model.getPassword());
        entity.setPlayers(PlayerEntityMapper.mapPlayerListToPlayerEntityList(model.getPlayers()));
        entity.setState(model.getState().name());
        entity.setCurrentTurnNumber(model.getCurrentTurnNumber());
        return entity;
    }
}
