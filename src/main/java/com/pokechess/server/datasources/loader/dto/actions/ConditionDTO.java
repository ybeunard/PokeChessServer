package com.pokechess.server.datasources.loader.dto.actions;

import org.springframework.lang.Nullable;

public class ConditionDTO {
    private String type;
    private String target;
    private Integer level;
    private String synergy;
    private String typePokemon;
    private Integer successRate;

    @Nullable
    public String getType() {
        return type;
    }

    @Nullable
    public String getTarget() {
        return target;
    }

    @Nullable
    public Integer getLevel() {
        return level;
    }

    @Nullable
    public String getSynergy() {
        return synergy;
    }

    @Nullable
    public String getTypePokemon() {
        return typePokemon;
    }

    @Nullable
    public Integer getSuccessRate() {
        return successRate;
    }
}
