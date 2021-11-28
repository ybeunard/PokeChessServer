package com.pokechess.server.models.globals.game.conditions.impl;

import com.pokechess.server.datasources.database.card.entity.ConditionEntity;
import com.pokechess.server.datasources.loader.dto.actions.ConditionLoaderDTO;
import com.pokechess.server.exceptions.ValidationException;
import com.pokechess.server.exceptions.loading.ActionException;
import com.pokechess.server.models.enumerations.Type;
import com.pokechess.server.models.enumerations.actions.Target;
import com.pokechess.server.models.globals.game.conditions.Condition;
import com.pokechess.server.validators.GenericValidator;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.lang.NonNull;

public class HaveTypeTwoCondition implements Condition {
    public static final String CONDITION_NAME = "HAVE_TYPE_2";

    private Target target;
    private Type type;

    public HaveTypeTwoCondition(ConditionLoaderDTO dto) {
        try {
            this.setTarget(Target.getEnum(dto.getTarget()));
            this.setType(Type.getEnum(dto.getType()));
        } catch (ValidationException e) {
            throw ActionException.of(ActionException.ActionExceptionType.CONDITION_VALIDATION, CONDITION_NAME, e);
        }
    }

    @Override
    public ConditionEntity mapToEntity() {
        ConditionEntity entity = new ConditionEntity();
        entity.setConditionName(CONDITION_NAME);
        entity.setTarget(target.name());
        entity.setType(type.name());
        return entity;
    }

    @NonNull
    public String getConditionName() {
        return CONDITION_NAME;
    }

    @NonNull
    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        GenericValidator.notNull(target, "target");
        this.target = target;
    }

    @NonNull
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        GenericValidator.notNull(type, "type");
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof HaveTypeTwoCondition && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "HaveTypeTwoCondition [target=%s, type=%s]", this.target, this.type);
    }
}
