package com.pokechess.server.models.enumerations.actions;

import org.springframework.lang.Nullable;

public enum EffectType {
    CLEAR_STATUS, APPLY_STATUS, HEALTH_ALTERATION, TYPE_2_MODIFICATION, PRECISION_MODIFICATION, ATTACK_MODIFICATION, DEFENSE_MODIFICATION, LIFE_STEAL, ATTACK_CANNOT_FAIL, SWAP_PLACE,
    CAPTURE, COST_REDUCTION;

    @Nullable
    public static EffectType getEnum(String name) {
        for (EffectType e : values()) {
            if (e.name().equals(name)) {
                return e;
            }
        }
        return null;
    }
}
