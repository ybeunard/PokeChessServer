package com.pokechess.server.models.enumerations.actions;

import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

public enum DurationTime {
    ON_ATTACK(1), ONE_TURN(2), TWO_TURN(3), THREE_TURN(4), FOUR_TURN(5), FIVE_TURN(6), LOSE_ON_BENCH(7), LOSE_ON_KO(8), INFINITE(9);

    private final int value;

    DurationTime(int value) {
        this.value = value;
    }

    @NotNull
    public int getValue() {
        return value;
    }

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
