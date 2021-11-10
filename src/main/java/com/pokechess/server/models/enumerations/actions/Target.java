package com.pokechess.server.models.enumerations.actions;

import org.springframework.lang.Nullable;

public enum Target {
    ALLY_OFFENSIVE_COLUMN, ALLY_DEFENSIVE_COLUMN, ENEMY_OFFENSIVE_COLUMN, ENEMY_DEFENSIVE_COLUMN, CURRENT_POKEMON, CURRENT_ATTACK,
    TARGETED_POKEMON, BENCH_POKEMON;

    @Nullable
    public static Target getEnum(String name) {
        for (Target e : values()) {
            if (e.name().equals(name)) {
                return e;
            }
        }
        return null;
    }
}
