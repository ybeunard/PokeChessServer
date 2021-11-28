package com.pokechess.server.models.globals.game.effects.impl;

import com.pokechess.server.datasources.database.card.entity.EffectEntity;
import com.pokechess.server.datasources.loader.dto.actions.EffectLoaderDTO;
import com.pokechess.server.datasources.loader.mapper.ActionLoaderMapper;
import com.pokechess.server.exceptions.ValidationException;
import com.pokechess.server.exceptions.loading.ActionException;
import com.pokechess.server.models.enumerations.actions.ApplyWhen;
import com.pokechess.server.models.enumerations.actions.DurationTime;
import com.pokechess.server.models.enumerations.actions.Target;
import com.pokechess.server.models.enumerations.actions.TriggerWhen;
import com.pokechess.server.models.globals.game.conditions.Condition;
import com.pokechess.server.models.globals.game.effects.ApplyWhenEffect;
import com.pokechess.server.models.globals.game.effects.DurationEffect;
import com.pokechess.server.models.globals.game.effects.Effect;
import com.pokechess.server.models.globals.game.effects.TargetEffect;
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

import static com.pokechess.server.datasources.database.card.mapper.CardEntityMapper.mapObjectToString;

public class TriggerEffect implements ApplyWhenEffect, DurationEffect, TargetEffect {
    public static final String EFFECT_NAME = "TRIGGER_EFFECT";

    private ApplyWhen applyWhen;
    private List<Condition> conditions;
    private DurationTime duration;
    private Effect effect;
    private List<Target> targets;
    private TriggerWhen triggerWhen;

    public TriggerEffect(EffectLoaderDTO dto) {
        try {
            this.setApplyWhen(ApplyWhen.getEnum(dto.getApplyWhen()));
            this.setConditions(ActionLoaderMapper.mapConditionListFromConditionLoaderDTOList(dto.getConditions()));
            this.setDuration(DurationTime.getEnum(dto.getDuration()));
            this.setEffect(Optional.ofNullable(dto.getEffect())
                    .map(ActionLoaderMapper::mapEffectFromEffectLoaderDTO).orElse(null));
            this.setTargets(ActionLoaderMapper.mapTargetListFromStringList(dto.getTargets()));
            this.setTriggerWhen(TriggerWhen.getEnum(dto.getTriggerWhen()));
        } catch (ValidationException e) {
            throw ActionException.of(ActionException.ActionExceptionType.EFFECT_VALIDATION, EFFECT_NAME, e);
        } catch (ActionException e) {
            throw ActionException.of(ActionException.ActionExceptionType.EFFECT_VALIDATION, EFFECT_NAME, e);
        }
    }

    @Override
    public EffectEntity mapToEntity() {
        EffectEntity entity = new EffectEntity();
        Optional.ofNullable(applyWhen).map(ApplyWhen::name).ifPresent(entity::setApplyWhen);
        entity.setConditions(conditions.stream().map(Condition::mapToEntity).collect(Collectors.toList()));
        entity.setDuration(duration.name());
        entity.setEffect(effect.mapToEntity());
        entity.setEffectName(EFFECT_NAME);
        entity.setTargets(mapObjectToString(targets));
        entity.setTriggerWhen(triggerWhen.name());
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
    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        GenericValidator.notNull(effect, "effect");
        this.effect = effect;
    }

    @NonNull
    public List<Target> getTargets() {
        return targets;
    }

    public void setTargets(List<Target> targets) {
        GenericValidator.notEmpty(targets, "targets");
        this.targets = targets;
    }

    @NonNull
    public TriggerWhen getTriggerWhen() {
        return triggerWhen;
    }

    public void setTriggerWhen(TriggerWhen triggerWhen) {
        GenericValidator.notNull(triggerWhen, "triggerWhen");
        this.triggerWhen = triggerWhen;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof TriggerEffect && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "TriggerEffect [triggerWhen=%s, targets=%s, duration=%s, effect=%s, applyWhen=%s, conditions=%s]", this.triggerWhen, this.targets, this.duration, this.effect, this.applyWhen, this.conditions);
    }
}
