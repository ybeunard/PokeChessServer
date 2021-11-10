package com.pokechess.server.models.enumerations;

import org.springframework.lang.Nullable;

public enum Synergy {
    STEEL, FIGHTING, DRAGON, WATER, ELECTRIC, FAIRY, FIRE, ICE, BUG, NORMAL, GRASS,
    POISON, GROUND, FLYING, PSYCHIC, ROCK, GHOST, DARK;

    @Nullable
    public static Synergy getEnum(String name) {
        for (Synergy e : values()) {
            if (e.name().equals(name)) {
                return e;
            }
        }
        return null;
    }
}
