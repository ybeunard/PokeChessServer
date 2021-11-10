package com.pokechess.server.models.enumerations;

import org.springframework.lang.Nullable;

public enum Generation {
    NO_GENERATION, FIRST_GENERATION, SECOND_GENERATION, THIRD_GENERATION, FOURTH_GENERATION, FIFTH_GENERATION,
    SIXTH_GENERATION, SEVENTH_GENERATION, EIGHTH_GENERATION;

    @Nullable
    public static Generation getEnum(String name) {
        for (Generation e : values()) {
            if (e.name().equals(name)) {
                return e;
            }
        }
        return null;
    }
}
