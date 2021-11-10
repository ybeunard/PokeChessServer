package com.pokechess.server.models.enumerations;

import org.springframework.lang.Nullable;

public enum PokemonStatus {
    PAR, PSN, FRZ, SLP, FER, CNF, BRN, FNT, TRAP;

    @Nullable
    public static PokemonStatus getEnum(String name) {
        for (PokemonStatus e : values()) {
            if (e.name().equals(name)) {
                return e;
            }
        }
        return null;
    }
}
