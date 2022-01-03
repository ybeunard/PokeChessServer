package com.pokechess.server.models.globals.game.effects.impl;

import com.pokechess.server.datasources.database.card.entity.EffectEntity;
import com.pokechess.server.datasources.loader.dto.actions.EffectLoaderDTO;
import com.pokechess.server.exceptions.ValidationException;
import com.pokechess.server.exceptions.loading.ActionException;
import com.pokechess.server.models.enumerations.actions.ApplyWhen;
import com.pokechess.server.models.globals.game.conditions.Condition;
import com.pokechess.server.models.globals.game.effects.ApplyWhenEffect;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.pokechess.server.datasources.database.card.mapper.CardEntityMapper.mapConditionListFromConditionEntityList;
import static com.pokechess.server.datasources.loader.mapper.ActionLoaderMapper.mapConditionListFromConditionLoaderDTOList;

public class DoNothingEffect implements ApplyWhenEffect {
    public static final String EFFECT_NAME = "DO_NOTHING";

    private ApplyWhen applyWhen;
    private List<Condition> conditions;

    public DoNothingEffect(EffectLoaderDTO dto) {
        try {
            this.setApplyWhen(ApplyWhen.getEnum(dto.getApplyWhen()));
            this.setConditions(mapConditionListFromConditionLoaderDTOList(dto.getConditions()));
        } catch (ValidationException e) {
            throw ActionException.of(ActionException.ActionExceptionType.EFFECT_VALIDATION, EFFECT_NAME, e);
        } catch (ActionException e) {
            throw ActionException.of(ActionException.ActionExceptionType.EFFECT_VALIDATION, EFFECT_NAME, e);
        }
    }

    public DoNothingEffect(EffectEntity entity) {
        this.setApplyWhen(ApplyWhen.getEnum(entity.getApplyWhen()));
        this.setConditions(mapConditionListFromConditionEntityList(entity.getConditions()));
    }

    @Override
    public EffectEntity mapToEntity() {
        EffectEntity entity = new EffectEntity();
        Optional.ofNullable(applyWhen).map(ApplyWhen::name).ifPresent(entity::setApplyWhen);
        entity.setConditions(conditions.stream().map(Condition::mapToEntity).collect(Collectors.toList()));
        entity.setEffectName(EFFECT_NAME);
        return entity;
    }

    @NonNull
    public String getEffectName() {
        return EFFECT_NAME;
    }

    @Nullable
    public ApplyWhen getApplyWhen() {
        return applyWhen;
    }

    public void setApplyWhen(ApplyWhen applyWhen) {
        this.applyWhen = applyWhen;
    }

    @NonNull
    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        if (Objects.isNull(conditions)) {
            conditions = new ArrayList<>();
        }
        this.conditions = conditions;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof DoNothingEffect && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "DoNothingEffect [applyWhen=%s, conditions=%s]", this.applyWhen, this.conditions);
    }
}
