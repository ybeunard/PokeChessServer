package com.pokechess.server.models.enumerations.actions;

import org.springframework.lang.Nullable;

public enum DurationTime {
    ACTIVE, THIS_TURN, LOSE_ON_TRIGGER, LOSE_ON_BENCH, INFINITE;

    @Nullable
    public static DurationTime getEnum(String name) {
        for (DurationTime e : values()) {
            if (e.name().equals(name)) {
                return e;
            }
        }
        return null;
    }
}
