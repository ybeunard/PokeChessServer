package com.pokechess.server.datasources.database.user.mapper;

import com.pokechess.server.datasources.database.user.entity.UserEntity;
import com.pokechess.server.models.globals.user.User;

public class UserEntityMapper {
    public static User mapUserFromUserEntity(UserEntity entity) {
        return User.builder().id(entity.getId()).username(entity.getUsername())
                .passwordHashed(entity.getPasswordHashed())
                .trainerName(entity.getTrainerName()).accessTokenId(entity.getAccessTokenId())
                .refreshTokenId(entity.getRefreshTokenId()).build();
    }

    public static UserEntity mapUserToUserEntity(User model) {
        UserEntity entity = new UserEntity();
        entity.setId(model.getId());
        entity.setUsername(model.getUsername());
        entity.setPasswordHashed(model.getPasswordHashed());
        entity.setTrainerName(model.getTrainerName());
        entity.setAccessTokenId(model.getAccessTokenId());
        entity.setRefreshTokenId(model.getRefreshTokenId());
        return entity;
    }
}
