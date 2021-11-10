package com.pokechess.server.datasources.loader.dto.actions;

import org.springframework.lang.Nullable;

import java.util.List;

public class EffectDTO {
    private String type;
    private TriggerDTO trigger;
    private List<ConditionDTO> conditions;
    private List<String> targets;
    private Integer level;
    private List<Integer> levels;
    private String status;
    private String typePokemon;
    private String duration;

    @Nullable
    public String getType() {
        return type;
    }

    @Nullable
    public TriggerDTO getTrigger() {
        return trigger;
    }

    @Nullable
    public List<ConditionDTO> getConditions() {
        return conditions;
    }

    @Nullable
    public List<String> getTargets() {
        return targets;
    }

    @Nullable
    public Integer getLevel() {
        return level;
    }

    @Nullable
    public List<Integer> getLevels() {
        return levels;
    }

    @Nullable
    public String getStatus() {
        return status;
    }

    @Nullable
    public String getTypePokemon() {
        return typePokemon;
    }

    @Nullable
    public String getDuration() {
        return duration;
    }
}
