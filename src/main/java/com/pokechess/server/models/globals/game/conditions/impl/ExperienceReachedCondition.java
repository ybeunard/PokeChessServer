package com.pokechess.server.models.globals.game.conditions.impl;

import com.pokechess.server.datasources.database.card.entity.ConditionEntity;
import com.pokechess.server.datasources.loader.dto.actions.ConditionLoaderDTO;
import com.pokechess.server.exceptions.ValidationException;
import com.pokechess.server.exceptions.loading.ActionException;
import com.pokechess.server.models.globals.game.conditions.Condition;
import com.pokechess.server.validators.GenericValidator;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.lang.NonNull;

public class ExperienceReachedCondition implements Condition {
    public static final String CONDITION_NAME = "EXPERIENCE_REACHED";

    private Integer value;

    public ExperienceReachedCondition(ConditionLoaderDTO dto) {
        try {
            this.setValue(dto.getValue());
        } catch (ValidationException e) {
            throw ActionException.of(ActionException.ActionExceptionType.CONDITION_VALIDATION, CONDITION_NAME, e);
        }
    }

    @Override
    public ConditionEntity mapToEntity() {
        ConditionEntity entity = new ConditionEntity();
        entity.setConditionName(CONDITION_NAME);
        entity.setValue(value);
        return entity;
    }

    @NonNull
    public String getConditionName() {
        return CONDITION_NAME;
    }

    @NonNull
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        GenericValidator.notNull(value, "value");
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ExperienceReachedCondition && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "ExperienceReachedCondition [value=%s]", this.value);
    }
}
