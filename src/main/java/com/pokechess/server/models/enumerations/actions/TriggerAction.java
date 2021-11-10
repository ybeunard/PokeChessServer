package com.pokechess.server.models.enumerations.actions;

import org.springframework.lang.Nullable;

public enum TriggerAction {
    OFFENSIVE_ATTACK, ATTACK_RECEIVE, STATUS_CHANGE;

    @Nullable
    public static TriggerAction getEnum(String name) {
        for (TriggerAction e : values()) {
            if (e.name().equals(name)) {
                return e;
            }
        }
        return null;
    }
}
