package com.pokechess.server.datasources.database.user;

import com.pokechess.server.datasources.database.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDatasource extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByTrainerName(String trainerName);
}
