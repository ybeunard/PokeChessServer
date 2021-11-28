package com.pokechess.server.models.enumerations.actions;

import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.List;

public enum Target {
    OFFENSIVE_ALLY, DEFENSIVE_ALLY, OFFENSIVE_ENEMY, DEFENSIVE_ENEMY, OFFENSIVE_ENEMY_LEFT, OFFENSIVE_ENEMY_RIGHT, CURRENT_POKEMON;

    @Nullable
    public static Target getEnum(String name) {
        for (Target e : values()) {
            if (e.name().equals(name)) {
                return e;
            }
        }
        return null;
    }

    public static final List<Target> pokemonTargets = Arrays.asList(OFFENSIVE_ALLY, DEFENSIVE_ALLY, OFFENSIVE_ENEMY,
            DEFENSIVE_ENEMY, OFFENSIVE_ENEMY_LEFT, OFFENSIVE_ENEMY_RIGHT, CURRENT_POKEMON);
}
