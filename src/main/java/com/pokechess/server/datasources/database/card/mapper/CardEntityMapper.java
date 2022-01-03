package com.pokechess.server.datasources.database.card.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokechess.server.datasources.database.card.entity.ConditionEntity;
import com.pokechess.server.datasources.database.card.entity.EffectEntity;
import com.pokechess.server.exceptions.ApiException;
import com.pokechess.server.exceptions.loading.ActionException;
import com.pokechess.server.models.globals.game.conditions.Condition;
import com.pokechess.server.models.globals.game.conditions.impl.*;
import com.pokechess.server.models.globals.game.effects.ApplyWhenEffect;
import com.pokechess.server.models.globals.game.effects.Effect;
import com.pokechess.server.models.globals.game.effects.impl.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CardEntityMapper {
    public static List<ConditionEntity> mapConditionListToConditionEntityList(List<Condition> modelList) {
        return modelList.stream().map(Condition::mapToEntity)
                .collect(Collectors.toList());
    }

    public static List<ApplyWhenEffect> mapApplyWhenEffectListFromEffectEntityList(List<com.pokechess.server.datasources.database.card.entity.EffectEntity> entityList) {
        if (Objects.isNull(entityList)) {
            return new ArrayList<>();
        }
        return entityList.stream().map(CardEntityMapper::mapApplyWhenEffectFromEffectEntity).collect(Collectors.toList());
    }

    public static ApplyWhenEffect mapApplyWhenEffectFromEffectEntity(EffectEntity entity) {
        return switch (entity.getEffectName()) {
            case ApplyStatusEffect.EFFECT_NAME -> new ApplyStatusEffect(entity);
            case ApplyTrapEffect.EFFECT_NAME -> new ApplyTrapEffect(entity);
            case AttackModificationEffect.EFFECT_NAME -> new AttackModificationEffect(entity);
            case BonusDamageEffect.EFFECT_NAME -> new BonusDamageEffect(entity);
            case CannotFailEffect.EFFECT_NAME -> new CannotFailEffect(entity);
            case ChangeSecondTypeEffect.EFFECT_NAME -> new ChangeSecondTypeEffect(entity);
            case CumulateDamageEffect.EFFECT_NAME -> new CumulateDamageEffect(entity);
            case DamageBySynergyLevelEffect.EFFECT_NAME -> new DamageBySynergyLevelEffect(entity);
            case DefenseModificationEffect.EFFECT_NAME -> new DefenseModificationEffect(entity);
            case DeleteDefenseBonusEffect.EFFECT_NAME -> new DeleteDefenseBonusEffect(entity);
            case DoNothingEffect.EFFECT_NAME -> new DoNothingEffect(entity);
            case HealingEffect.EFFECT_NAME -> new HealingEffect(entity);
            case IgnoreDefenseEffect.EFFECT_NAME -> new IgnoreDefenseEffect(entity);
            case LifeStealEffect.EFFECT_NAME -> new LifeStealEffect(entity);
            case MultiPercentageEffect.EFFECT_NAME -> new MultiPercentageEffect(entity);
            case PrecisionModificationEffect.EFFECT_NAME -> new PrecisionModificationEffect(entity);
            case ProtectStatusChangeEffect.EFFECT_NAME -> new ProtectStatusChangeEffect(entity);
            case SpeedModificationEffect.EFFECT_NAME -> new SpeedModificationEffect(entity);
            case SwapTargetToSupportEffect.EFFECT_NAME -> new SwapTargetToSupportEffect(entity);
            case TriggerEffect.EFFECT_NAME -> new TriggerEffect(entity);
            default -> throw new ActionException(ActionException.ActionExceptionType.EFFECT_VALIDATION, entity.getEffectName(), "unknown or is not 'ApplyWhenEffect'");
        };
    }

    public static List<Effect> mapEffectListFromEffectEntityList(List<EffectEntity> entityList) {
        if (Objects.isNull(entityList)) {
            return new ArrayList<>();
        }
        return entityList.stream().map(CardEntityMapper::mapEffectFromEffectEntity).collect(Collectors.toList());
    }

    public static Effect mapEffectFromEffectEntity(EffectEntity entity) {
        return switch (entity.getEffectName()) {
            case LeechSeedEffect.EFFECT_NAME -> new LeechSeedEffect(entity);
            default -> mapApplyWhenEffectFromEffectEntity(entity);
        };
    }

    public static List<Condition> mapConditionListFromConditionEntityList(List<ConditionEntity> entityList) {
        if (Objects.isNull(entityList)) {
            return new ArrayList<>();
        }
        return entityList.stream().map(CardEntityMapper::mapConditionFromConditionEntity).collect(Collectors.toList());
    }

    public static Condition mapConditionFromConditionEntity(ConditionEntity entity) {
        return switch (entity.getConditionName()) {
            case ActiveSynergyCondition.CONDITION_NAME -> new ActiveSynergyCondition(entity);
            case AttackAfterCondition.CONDITION_NAME -> new AttackAfterCondition(entity);
            case AttackBeforeCondition.CONDITION_NAME -> new AttackBeforeCondition(entity);
            case DetectSupportEffect.CONDITION_NAME -> new DetectSupportEffect(entity);
            case ExperienceReachedCondition.CONDITION_NAME -> new ExperienceReachedCondition(entity);
            case HaveTypeTwoCondition.CONDITION_NAME -> new HaveTypeTwoCondition(entity);
            case MultiPercentageCondition.CONDITION_NAME -> new MultiPercentageCondition(entity);
            case PercentageCondition.CONDITION_NAME -> new PercentageCondition(entity);
            default -> throw new ActionException(ActionException.ActionExceptionType.CONDITION_VALIDATION, entity.getConditionName(), "condition unknown");
        };
    }

    public static String mapObjectToString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during json mapping");
        }
    }

    public static <T> List<T> mapObjectListFromString(String s, Class<T> clazz) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(s, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (JsonProcessingException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during json mapping");
        }
    }
}
