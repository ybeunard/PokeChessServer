package com.pokechess.server.models.globals.game.conditions.impl;

import com.pokechess.server.datasources.database.card.entity.ConditionEntity;
import com.pokechess.server.datasources.loader.dto.actions.ConditionLoaderDTO;
import com.pokechess.server.exceptions.ValidationException;
import com.pokechess.server.exceptions.loading.ActionException;
import com.pokechess.server.models.enumerations.actions.Target;
import com.pokechess.server.models.globals.game.conditions.Condition;
import com.pokechess.server.validators.GenericValidator;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.lang.NonNull;

public class AttackBeforeCondition implements Condition {
    public static final String CONDITION_NAME = "ATTACK_BEFORE";

    private Target target;

    public AttackBeforeCondition(ConditionLoaderDTO dto) {
        try {
            this.setTarget(Target.getEnum(dto.getTarget()));
        } catch (ValidationException e) {
            throw ActionException.of(ActionException.ActionExceptionType.CONDITION_VALIDATION, CONDITION_NAME, e);
        }
    }

    public AttackBeforeCondition(ConditionEntity entity) {
        this.setTarget(Target.getEnum(entity.getTarget()));
    }

    @Override
    public ConditionEntity mapToEntity() {
        ConditionEntity entity = new ConditionEntity();
        entity.setConditionName(CONDITION_NAME);
        entity.setTarget(target.name());
        return entity;
    }

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

    @Override
    public boolean equals(Object o) {
        return o instanceof AttackBeforeCondition && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "AttackBeforeCondition [target=%s]", this.target);
    }
}
