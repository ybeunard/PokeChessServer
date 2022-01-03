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
import com.pokechess.server.models.globals.game.effects.NameEffect;
import com.pokechess.server.models.globals.game.effects.PowerEffect;
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

public class CumulateDamageEffect implements ApplyWhenEffect, DurationEffect, NameEffect, PowerEffect {
    public static final String EFFECT_NAME = "CUMULATE_DAMAGE";

    private ApplyWhen applyWhen;
    private List<Condition> conditions;
    private DurationTime duration;
    private String name;
    private Integer maxCumulate;
    private Integer power;

    public CumulateDamageEffect(EffectLoaderDTO dto) {
        try {
            this.setApplyWhen(ApplyWhen.getEnum(dto.getApplyWhen()));
            this.setConditions(mapConditionListFromConditionLoaderDTOList(dto.getConditions()));
            this.setDuration(DurationTime.getEnum(dto.getDuration()));
            this.setName(dto.getName());
            this.setMaxCumulate(dto.getMaxCumulate());
            this.setPower(dto.getPower());
        } catch (ValidationException e) {
            throw ActionException.of(ActionException.ActionExceptionType.EFFECT_VALIDATION, EFFECT_NAME, e);
        } catch (ActionException e) {
            throw ActionException.of(ActionException.ActionExceptionType.EFFECT_VALIDATION, EFFECT_NAME, e);
        }
    }

    public CumulateDamageEffect(EffectEntity entity) {
        this.setApplyWhen(ApplyWhen.getEnum(entity.getApplyWhen()));
        this.setConditions(mapConditionListFromConditionEntityList(entity.getConditions()));
        this.setDuration(DurationTime.getEnum(entity.getDuration()));
        this.setName(entity.getName());
        this.setMaxCumulate(entity.getMaxCumulate());
        this.setPower(entity.getPower());
    }

    @Override
    public EffectEntity mapToEntity() {
        EffectEntity entity = new EffectEntity();
        Optional.ofNullable(applyWhen).map(ApplyWhen::name).ifPresent(entity::setApplyWhen);
        entity.setConditions(conditions.stream().map(Condition::mapToEntity).collect(Collectors.toList()));
        entity.setDuration(duration.name());
        entity.setEffectName(EFFECT_NAME);
        entity.setName(name);
        entity.setMaxCumulate(maxCumulate);
        entity.setPower(power);
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

    @NonNull
    public DurationTime getDuration() {
        return duration;
    }

    public void setDuration(DurationTime duration) {
        GenericValidator.notNull(duration, "duration");
        this.duration = duration;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        GenericValidator.notEmpty(name, "name");
        this.name = name;
    }

    @NonNull
    public Integer getMaxCumulate() {
        return maxCumulate;
    }

    public void setMaxCumulate(Integer maxCumulate) {
        GenericValidator.notNull(maxCumulate, "maxCumulate");
        GenericValidator.min(maxCumulate, 1, "maxCumulate");
        this.maxCumulate = maxCumulate;
    }

    @NonNull
    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        GenericValidator.notNull(power, "power");
        GenericValidator.min(power, 10, "power");
        GenericValidator.multiple(power, 10, "power");
        this.power = power;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof CumulateDamageEffect && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "CumulateDamageEffect [applyWhen=%s, conditions=%s, duration=%s, name=%s, maxCumulate=%s, power=%s]", this.applyWhen, this.conditions, this.duration, this.name, this.maxCumulate, this.power);
    }
}
