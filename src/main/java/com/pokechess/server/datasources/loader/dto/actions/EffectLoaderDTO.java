package com.pokechess.server.datasources.loader.dto.actions;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public class EffectLoaderDTO {
    private String applyWhen;
    private List<ConditionLoaderDTO> conditions;
    private String duration;
    private EffectLoaderDTO effect;
    private String effectName;
    private List<EffectLoaderDTO> effects;
    private String name;
    private Integer maxCumulate;
    private Integer percentage;
    private Integer power;
    private String status;
    private List<String> targets;
    private String triggerWhen;
    private String type;
    private Integer value;

    public String getApplyWhen() {
        return applyWhen;
    }

    public List<ConditionLoaderDTO> getConditions() {
        return conditions;
    }

    public String getDuration() {
        return duration;
    }

    public EffectLoaderDTO getEffect() {
        return effect;
    }

    public String getEffectName() {
        return effectName;
    }

    public List<EffectLoaderDTO> getEffects() {
        return effects;
    }

    public String getName() {
        return name;
    }

    public Integer getMaxCumulate() {
        return maxCumulate;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public Integer getPower() {
        return power;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getTargets() {
        return targets;
    }

    public String getTriggerWhen() {
        return triggerWhen;
    }

    public String getType() {
        return type;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof EffectLoaderDTO && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "EffectLoaderDTO [applyWhen=%s, conditions=%s, duration=%s, effect=%s, effectName=%s, effects=%s, name=%s, maxCumulate=%s, percentage=%s, power=%s, status=%s, targets=%s, triggerWhen=%s, type=%s, value=%s]", this.applyWhen, this.conditions, this.duration, this.effect, this.effectName, this.effects, this.name, this.maxCumulate, this.percentage, this.power, this.status, this.targets, this.triggerWhen, this.type, this.value);
    }
}
