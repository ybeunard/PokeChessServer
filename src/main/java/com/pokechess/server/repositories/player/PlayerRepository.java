package com.pokechess.server.repositories.player;

import com.pokechess.server.datasources.database.player.PlayerDatasource;
import org.springframework.stereotype.Repository;

@Repository
public class PlayerRepository {
    private final PlayerDatasource playerDatasource;

    public PlayerRepository(PlayerDatasource playerDatasource) {
        this.playerDatasource = playerDatasource;
    }

    public Boolean existsPlayerByUsername(String username) {
        return this.playerDatasource.existsByUser_Username(username);
    }
}
