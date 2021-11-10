package com.pokechess.server.models.enumerations;

import org.springframework.lang.Nullable;

public enum Type {
    NO_TYPE, STEEL, FIGHTING, DRAGON, WATER, ELECTRIC, FAIRY, FIRE, ICE, BUG, NORMAL, GRASS,
    POISON, GROUND, FLYING, PSYCHIC, ROCK, GHOST, DARK;

    @Nullable
    public static Type getEnum(String name) {
        for (Type e : values()) {
            if (e.name().equals(name)) {
                return e;
            }
        }
        return null;
    }
}
