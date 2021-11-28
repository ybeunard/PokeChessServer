package com.pokechess.server.datasources.loader.dto.actions;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public class ConditionLoaderDTO {
    private String conditionName;
    private Integer percentage;
    private List<Integer> percentages;
    private String target;
    private String type;
    private Integer value;

    public String getConditionName() {
        return conditionName;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public List<Integer> getPercentages() {
        return percentages;
    }

    public String getTarget() {
        return target;
    }

    public String getType() {
        return type;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ConditionLoaderDTO && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "ConditionLoaderDTO [conditionName=%s, percentage=%s, percentages=%s, target=%s, value=%s]", this.conditionName, this.percentage, this.percentages, this.target, this.value);
    }
}
