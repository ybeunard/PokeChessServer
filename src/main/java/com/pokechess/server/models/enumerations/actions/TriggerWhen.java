package com.pokechess.server.models.enumerations.actions;

import org.springframework.lang.Nullable;

public enum TriggerWhen {
    ON_START_FIGHT, ON_TARGETING, ON_DAMAGE_RECEIVE, BEFORE_KO, ON_KO, ON_END_FIGHT;

    @Nullable
    public static TriggerWhen getEnum(String name) {
        for (TriggerWhen e : values()) {
            if (e.name().equals(name)) {
                return e;
            }
        }
        return null;
    }
}
