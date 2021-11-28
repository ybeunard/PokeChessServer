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

import java.util.List;

import static com.pokechess.server.datasources.database.card.mapper.CardEntityMapper.mapObjectToString;

public class MultiPercentageCondition implements Condition {
    public static final String CONDITION_NAME = "MULTI_PERCENTAGE";

    private List<Integer> percentages;

    public MultiPercentageCondition(ConditionLoaderDTO dto) {
        try {
            this.setPercentages(dto.getPercentages());
        } catch (ValidationException e) {
            throw ActionException.of(ActionException.ActionExceptionType.CONDITION_VALIDATION, CONDITION_NAME, e);
        }
    }

    @Override
    public ConditionEntity mapToEntity() {
        ConditionEntity entity = new ConditionEntity();
        entity.setConditionName(CONDITION_NAME);
        entity.setPercentages(mapObjectToString(percentages));
        return entity;
    }

    @NonNull
    public String getConditionName() {
        return CONDITION_NAME;
    }

    @NonNull
    public List<Integer> getPercentages() {
        return percentages;
    }

    public void setPercentages(List<Integer> percentages) {
        GenericValidator.notEmpty(percentages, "percentages");
        percentages.forEach(percentage -> GenericValidator.min(percentage, 1, "percentages"));
        if (percentages.stream().reduce(0, Integer::sum) > 100 ) {
            throw new ValidationException("percentages", "sums of percentages cannot exceed 100");
        }
        this.percentages = percentages;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof MultiPercentageCondition && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "MultiPercentageCondition [percentages=%s]", this.percentages);
    }
}
