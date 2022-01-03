package com.pokechess.server.models.globals.game.conditions.impl;

import com.pokechess.server.datasources.database.card.entity.ConditionEntity;
import com.pokechess.server.datasources.loader.dto.actions.ConditionLoaderDTO;
import com.pokechess.server.exceptions.ValidationException;
import com.pokechess.server.exceptions.loading.ActionException;
import com.pokechess.server.models.enumerations.Type;
import com.pokechess.server.models.globals.game.conditions.Condition;
import com.pokechess.server.validators.GenericValidator;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.lang.NonNull;

import java.util.Arrays;

public class ActiveSynergyCondition implements Condition {
    public static final String CONDITION_NAME = "ACTIVE_SYNERGY";

    private Type type;
    private Integer value;

    public ActiveSynergyCondition(ConditionLoaderDTO dto) {
        try {
            this.setType(Type.getEnum(dto.getType()));
            this.setValue(dto.getValue());
        } catch (ValidationException e) {
            throw ActionException.of(ActionException.ActionExceptionType.CONDITION_VALIDATION, CONDITION_NAME, e);
        }
    }

    public ActiveSynergyCondition(ConditionEntity entity) {
        this.setType(Type.getEnum(entity.getType()));
        this.setValue(entity.getValue());
    }

    @Override
    public ConditionEntity mapToEntity() {
        ConditionEntity entity = new ConditionEntity();
        entity.setConditionName(CONDITION_NAME);
        entity.setType(type.name());
        entity.setValue(value);
        return entity;
    }

    @NonNull
    public String getConditionName() {
        return CONDITION_NAME;
    }

    @NonNull
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        GenericValidator.notNull(type, "type");
        this.type = type;
    }

    @NonNull
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        GenericValidator.notNull(value, "value");
        if (!Arrays.asList(1, 2, 3).contains(value)) {
            throw new ValidationException("value", value.toString(), "need to be 1, 2 or 3");
        }
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ActiveSynergyCondition && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "ActiveSynergyCondition [type=%s, value=%s]", this.type, this.value);
    }
}
