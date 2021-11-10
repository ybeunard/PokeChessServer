package com.pokechess.server.models.enumerations.actions;

import org.springframework.lang.Nullable;

public enum ConditionType {
    EXPERIENCE_EQUALS, TYPE_EQUALS, TYPE_NOT_EQUALS, TYPE_2_EQUALS, DICE_ROLL, SYNERGY, STATUS;

    @Nullable
    public static ConditionType getEnum(String name) {
        for (ConditionType e : values()) {
            if (e.name().equals(name)) {
                return e;
            }
        }
        return null;
    }
}
