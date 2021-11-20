package com.pokechess.server.models.enumerations;

import org.springframework.lang.Nullable;

public enum PartyState {
    CREATION;

    @Nullable
    public static PartyState getEnum(String name) {
        for (PartyState e : values()) {
            if (e.name().equals(name)) {
                return e;
            }
        }
        return null;
    }
}
