package com.pokechess.server.models.globals.game.conditions.impl;

import com.pokechess.server.datasources.database.card.entity.ConditionEntity;
import com.pokechess.server.datasources.loader.dto.actions.ConditionLoaderDTO;
import com.pokechess.server.exceptions.ValidationException;
import com.pokechess.server.exceptions.loading.ActionException;
import com.pokechess.server.models.globals.game.conditions.Condition;
import com.pokechess.server.validators.GenericValidator;
import org.springframework.lang.NonNull;

public class PercentageCondition implements Condition {
    public static final String CONDITION_NAME = "PERCENTAGE";

    private Integer percentage;

    public PercentageCondition(ConditionLoaderDTO dto) {
        try {
            this.setPercentage(dto.getPercentage());
        } catch (ValidationException e) {
            throw ActionException.of(ActionException.ActionExceptionType.CONDITION_VALIDATION, CONDITION_NAME, e);
        }
    }

    @Override
    public ConditionEntity mapToEntity() {
        ConditionEntity entity = new ConditionEntity();
        entity.setConditionName(CONDITION_NAME);
        entity.setPercentage(percentage);
        return entity;
    }

    @NonNull
    public String getConditionName() {
        return CONDITION_NAME;
    }

    @NonNull
    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        GenericValidator.notNull(percentage, "percentage");
        GenericValidator.min(percentage, 1, "percentage");
        GenericValidator.max(percentage, 99, "percentage");
        this.percentage = percentage;
    }
}
