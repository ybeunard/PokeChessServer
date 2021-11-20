package com.pokechess.server.datasources.database.player;

import com.pokechess.server.datasources.database.player.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerDatasource extends JpaRepository<PlayerEntity, Integer> {
    Boolean existsByUser_Username(String username);
}
