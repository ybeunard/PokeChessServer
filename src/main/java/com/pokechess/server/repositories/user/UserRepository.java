package com.pokechess.server.repositories.user;

import com.pokechess.server.datasources.database.user.UserDatasource;
import com.pokechess.server.datasources.database.user.entity.UserEntity;
import com.pokechess.server.datasources.database.user.mapper.UserEntityMapper;
import com.pokechess.server.exceptions.UserException;
import com.pokechess.server.models.globals.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserRepository {
    private final UserDatasource userDatasource;

    public UserRepository(UserDatasource userDatasource) {
        this.userDatasource = userDatasource;
    }

    /**
     *
     * @throws UserException User not found exception
     */
    public User getByUsername(String username) {
        return this.userDatasource.findByUsername(username)
                .map(UserEntityMapper::mapUserFromUserEntity)
                .orElseThrow(() -> UserException.of(UserException.UserExceptionType.USER_NOT_FOUND));
    }

    /**
     *
     * @throws UserException Username already exist exception
     * @throws UserException Trainer name already exist exception
     */
    public User create(User user) {
        if (this.userDatasource.findByUsername(user.getUsername()).isPresent())
            throw UserException.of(UserException.UserExceptionType.USERNAME_ALREADY_EXIST);
        if (this.userDatasource.findByTrainerName(user.getTrainerName()).isPresent())
            throw UserException.of(UserException.UserExceptionType.TRAINER_NAME_ALREADY_EXIST);
        UserEntity newUserEntity = this.userDatasource.save(UserEntityMapper.mapUserToUserEntity(user));
        return UserEntityMapper.mapUserFromUserEntity(newUserEntity);
    }

    public String patchAccessTokenId(String username, String accessTokenId) {
        UserEntity userEntity = this.userDatasource.findByUsername(username)
                .orElseThrow(() -> UserException.of(UserException.UserExceptionType.USER_NOT_FOUND));
        userEntity.setAccessTokenId(accessTokenId);
        return this.userDatasource.save(userEntity).getAccessTokenId();
    }

    public String patchRefreshTokenId(String username, String refreshTokenId) {
        UserEntity userEntity = this.userDatasource.findByUsername(username)
                .orElseThrow(() -> UserException.of(UserException.UserExceptionType.USER_NOT_FOUND));
        userEntity.setRefreshTokenId(refreshTokenId);
        return this.userDatasource.save(userEntity).getRefreshTokenId();
    }
}
