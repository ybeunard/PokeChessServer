package com.pokechess.server.datasources.database.player;

import com.pokechess.server.datasources.database.player.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerDatasource extends JpaRepository<PlayerEntity, Integer> {
    Boolean existsByUser_Username(String username);
    Optional<PlayerEntity> findByUser_Username(String username);
}
