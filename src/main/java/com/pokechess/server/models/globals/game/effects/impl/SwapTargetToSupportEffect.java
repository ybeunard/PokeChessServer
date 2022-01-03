package com.pokechess.server.models.globals.game.effects.impl;

import com.pokechess.server.datasources.database.card.entity.EffectEntity;
import com.pokechess.server.datasources.loader.dto.actions.EffectLoaderDTO;
import com.pokechess.server.exceptions.ValidationException;
import com.pokechess.server.exceptions.loading.ActionException;
import com.pokechess.server.models.enumerations.actions.ApplyWhen;
import com.pokechess.server.models.enumerations.actions.DurationTime;
import com.pokechess.server.models.globals.game.conditions.Condition;
import com.pokechess.server.models.globals.game.effects.ApplyWhenEffect;
import com.pokechess.server.models.globals.game.effects.DurationEffect;
import com.pokechess.server.validators.GenericValidator;
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

public class SwapTargetToSupportEffect implements ApplyWhenEffect, DurationEffect {
    public static final String EFFECT_NAME = "SWAP_TARGET_TO_SUPPORT";

    private ApplyWhen applyWhen;
    private List<Condition> conditions;
    private DurationTime duration;

    public SwapTargetToSupportEffect(EffectLoaderDTO dto) {
        try {
            this.setApplyWhen(ApplyWhen.getEnum(dto.getApplyWhen()));
            this.setConditions(mapConditionListFromConditionLoaderDTOList(dto.getConditions()));
            this.setDuration(DurationTime.getEnum(dto.getDuration()));
        } catch (ValidationException e) {
            throw ActionException.of(ActionException.ActionExceptionType.EFFECT_VALIDATION, EFFECT_NAME, e);
        } catch (ActionException e) {
            throw ActionException.of(ActionException.ActionExceptionType.EFFECT_VALIDATION, EFFECT_NAME, e);
        }
    }

    public SwapTargetToSupportEffect(EffectEntity entity) {
        this.setApplyWhen(ApplyWhen.getEnum(entity.getApplyWhen()));
        this.setConditions(mapConditionListFromConditionEntityList(entity.getConditions()));
        this.setDuration(DurationTime.getEnum(entity.getDuration()));
    }

    @Override
    public EffectEntity mapToEntity() {
        EffectEntity entity = new EffectEntity();
        Optional.ofNullable(applyWhen).map(ApplyWhen::name).ifPresent(entity::setApplyWhen);
        entity.setConditions(conditions.stream().map(Condition::mapToEntity).collect(Collectors.toList()));
        entity.setDuration(duration.name());
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

    public void setConditions(List<Condition> conditions)  {
        if (Objects.isNull(conditions)) {
            conditions = new ArrayList<>();
        }
        this.conditions = conditions;
    }

    @NonNull
    public DurationTime getDuration() {
        return duration;
    }

    public void setDuration(DurationTime duration) {
        GenericValidator.notNull(duration, "duration");
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof SwapTargetToSupportEffect && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "SwapTargetToSupportEffect [applyWhen=%s, conditions=%s, duration=%s]", this.applyWhen, this.conditions, this.duration);
    }
}
