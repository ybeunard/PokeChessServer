package com.pokechess.server.datasources.loader.dto.actions;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public class AttackLoaderDTO {
    private String name;
    private String description;
    private String type;
    private List<String> targets;
    private Integer power;
    private Integer precision;
    private Integer priority;
    private List<EffectLoaderDTO> effects;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public List<String> getTargets() {
        return targets;
    }

    public Integer getPower() {
        return power;
    }

    public Integer getPrecision() {
        return precision;
    }

    public Integer getPriority() {
        return priority;
    }

    public List<EffectLoaderDTO> getEffects() {
        return effects;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof AttackLoaderDTO && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "AttackDTO [name=%s, description=%s, type=%s, targets=%s, power=%s, precision=%s, priority=%s, effects=%s]", this.name, this.description, this.type, this.targets, this.power, this.precision, this.priority, this.effects);
    }
}
