package com.pokechess.server.models.globals.game.effects.impl;

import com.pokechess.server.datasources.database.card.entity.EffectEntity;
import com.pokechess.server.datasources.loader.dto.actions.EffectLoaderDTO;
import com.pokechess.server.exceptions.ValidationException;
import com.pokechess.server.exceptions.loading.ActionException;
import com.pokechess.server.models.enumerations.actions.ApplyWhen;
import com.pokechess.server.models.globals.game.conditions.Condition;
import com.pokechess.server.models.globals.game.conditions.impl.MultiPercentageCondition;
import com.pokechess.server.models.globals.game.effects.ApplyWhenEffect;
import com.pokechess.server.models.globals.game.effects.Effect;
import com.pokechess.server.validators.GenericValidator;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.pokechess.server.datasources.database.card.mapper.CardEntityMapper.mapConditionListFromConditionEntityList;
import static com.pokechess.server.datasources.database.card.mapper.CardEntityMapper.mapEffectListFromEffectEntityList;
import static com.pokechess.server.datasources.loader.mapper.ActionLoaderMapper.mapConditionListFromConditionLoaderDTOList;
import static com.pokechess.server.datasources.loader.mapper.ActionLoaderMapper.mapEffectListFromEffectLoaderDTOList;

public class MultiPercentageEffect implements ApplyWhenEffect {
    public static final String EFFECT_NAME = "MULTI_PERCENTAGE_EFFECT";

    private ApplyWhen applyWhen;
    private List<Condition> conditions;
    private List<Effect> effects;

    public MultiPercentageEffect(EffectLoaderDTO dto) {
        try {
            this.setApplyWhen(ApplyWhen.getEnum(dto.getApplyWhen()));
            this.setConditions(mapConditionListFromConditionLoaderDTOList(dto.getConditions()));
            this.setEffects(mapEffectListFromEffectLoaderDTOList(dto.getEffects()));
        } catch (ValidationException e) {
            throw ActionException.of(ActionException.ActionExceptionType.EFFECT_VALIDATION, EFFECT_NAME, e);
        } catch (ActionException e) {
            throw ActionException.of(ActionException.ActionExceptionType.EFFECT_VALIDATION, EFFECT_NAME, e);
        }
    }

    public MultiPercentageEffect(EffectEntity entity) {
        this.setApplyWhen(ApplyWhen.getEnum(entity.getApplyWhen()));
        this.setConditions(mapConditionListFromConditionEntityList(entity.getConditions()));
        this.setEffects(mapEffectListFromEffectEntityList(entity.getEffects()));
    }

    @Override
    public EffectEntity mapToEntity() {
        EffectEntity entity = new EffectEntity();
        Optional.ofNullable(applyWhen).map(ApplyWhen::name).ifPresent(entity::setApplyWhen);
        entity.setConditions(conditions.stream().map(Condition::mapToEntity).collect(Collectors.toList()));
        entity.setEffectName(EFFECT_NAME);
        entity.setEffects(effects.stream().map(Effect::mapToEntity).collect(Collectors.toList()));
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
        GenericValidator.notEmpty(conditions, "conditions");
        if (conditions.size() != 1 || !(conditions.get(0) instanceof MultiPercentageCondition)) {
            throw new ValidationException("conditions", "need to contains only one MultiPercentageCondition");
        }
        this.conditions = conditions;
    }

    @NonNull
    public List<Effect> getEffects() {
        return effects;
    }

    public void setEffects(List<Effect> effects) {
        GenericValidator.notEmpty(effects, "effects");
        this.effects = effects;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof MultiPercentageEffect && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "MultiPercentageEffect [applyWhen=%s, conditions=%s, effects=%s]", this.applyWhen, this.conditions, this.effects);
    }
}
