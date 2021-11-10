package com.pokechess.server.datasources.loader.dto.actions;

import org.springframework.lang.Nullable;

import java.util.List;

public class AttackDTO {
    private String type;
    private Integer power;
    private Integer precision;
    private List<EffectDTO> effects;

    @Nullable
    public String getType() {
        return type;
    }

    @Nullable
    public Integer getPower() {
        return power;
    }

    @Nullable
    public Integer getPrecision() {
        return precision;
    }

    @Nullable
    public List<EffectDTO> getEffects() {
        return effects;
    }
}
