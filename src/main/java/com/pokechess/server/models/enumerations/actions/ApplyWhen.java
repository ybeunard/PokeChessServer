package com.pokechess.server.models.enumerations.actions;

import org.springframework.lang.Nullable;

public enum ApplyWhen {
    START_FIGHT, START_ATTACK, BEFORE_PRECISION_RESOLUTION, BEFORE_STATUS_RESOLUTION, BEFORE_DAMAGE, END_ATTACK, BEFORE_KO, END_FIGHT;

    @Nullable
    public static ApplyWhen getEnum(String name) {
        for (ApplyWhen e : values()) {
            if (e.name().equals(name)) {
                return e;
            }
        }
        return null;
    }
}
