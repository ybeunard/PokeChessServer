package com.pokechess.server.datasources.loader.mapper;

import com.pokechess.server.datasources.loader.dto.actions.ConditionLoaderDTO;
import com.pokechess.server.datasources.loader.dto.actions.EffectLoaderDTO;
import com.pokechess.server.exceptions.loading.ActionException;
import com.pokechess.server.models.enumerations.actions.Target;
import com.pokechess.server.models.globals.game.conditions.Condition;
import com.pokechess.server.models.globals.game.conditions.impl.*;
import com.pokechess.server.models.globals.game.effects.ApplyWhenEffect;
import com.pokechess.server.models.globals.game.effects.Effect;
import com.pokechess.server.models.globals.game.effects.impl.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ActionLoaderMapper {

    public static List<ApplyWhenEffect> mapApplyWhenEffectListFromEffectLoaderDTOList(List<EffectLoaderDTO> dtoList) {
        if (Objects.isNull(dtoList)) {
            return new ArrayList<>();
        }
        return dtoList.stream().map(ActionLoaderMapper::mapApplyWhenEffectFromEffectLoaderDTO).collect(Collectors.toList());
    }

    public static ApplyWhenEffect mapApplyWhenEffectFromEffectLoaderDTO(EffectLoaderDTO dto) {
        if (Objects.isNull(dto.getEffectName()))
            throw new ActionException(ActionException.ActionExceptionType.EFFECT_VALIDATION, "effectName", "cannot be null");
        return switch (dto.getEffectName()) {
            case ApplyStatusEffect.EFFECT_NAME -> new ApplyStatusEffect(dto);
            case ApplyTrapEffect.EFFECT_NAME -> new ApplyTrapEffect(dto);
            case AttackModificationEffect.EFFECT_NAME -> new AttackModificationEffect(dto);
            case BonusDamageEffect.EFFECT_NAME -> new BonusDamageEffect(dto);
            case CannotFailEffect.EFFECT_NAME -> new CannotFailEffect(dto);
            case ChangeSecondTypeEffect.EFFECT_NAME -> new ChangeSecondTypeEffect(dto);
            case CumulateDamageEffect.EFFECT_NAME -> new CumulateDamageEffect(dto);
            case DamageBySynergyLevelEffect.EFFECT_NAME -> new DamageBySynergyLevelEffect(dto);
            case DefenseModificationEffect.EFFECT_NAME -> new DefenseModificationEffect(dto);
            case DeleteDefenseBonusEffect.EFFECT_NAME -> new DeleteDefenseBonusEffect(dto);
            case DoNothingEffect.EFFECT_NAME -> new DoNothingEffect(dto);
            case HealingEffect.EFFECT_NAME -> new HealingEffect(dto);
            case IgnoreDefenseEffect.EFFECT_NAME -> new IgnoreDefenseEffect(dto);
            case LifeStealEffect.EFFECT_NAME -> new LifeStealEffect(dto);
            case MultiPercentageEffect.EFFECT_NAME -> new MultiPercentageEffect(dto);
            case PrecisionModificationEffect.EFFECT_NAME -> new PrecisionModificationEffect(dto);
            case ProtectStatusChangeEffect.EFFECT_NAME -> new ProtectStatusChangeEffect(dto);
            case SpeedModificationEffect.EFFECT_NAME -> new SpeedModificationEffect(dto);
            case SwapTargetToSupportEffect.EFFECT_NAME -> new SwapTargetToSupportEffect(dto);
            case TriggerEffect.EFFECT_NAME -> new TriggerEffect(dto);
            default -> throw new ActionException(ActionException.ActionExceptionType.EFFECT_VALIDATION, dto.getEffectName(), "unknown or is not 'ApplyWhenEffect'");
        };
    }

    public static List<Effect> mapEffectListFromEffectLoaderDTOList(List<EffectLoaderDTO> dtoList) {
        if (Objects.isNull(dtoList)) {
            return new ArrayList<>();
        }
        return dtoList.stream().map(ActionLoaderMapper::mapEffectFromEffectLoaderDTO).collect(Collectors.toList());
    }

    public static Effect mapEffectFromEffectLoaderDTO(EffectLoaderDTO dto) {
        if (Objects.isNull(dto.getEffectName()))
            throw new ActionException(ActionException.ActionExceptionType.EFFECT_VALIDATION, "effectName", "cannot be null");
        return switch (dto.getEffectName()) {
            case LeechSeedEffect.EFFECT_NAME -> new LeechSeedEffect(dto);
            default -> mapApplyWhenEffectFromEffectLoaderDTO(dto);
        };
    }

    public static List<Condition> mapConditionListFromConditionLoaderDTOList(List<ConditionLoaderDTO> dtoList) {
        if (Objects.isNull(dtoList)) {
            return new ArrayList<>();
        }
        return dtoList.stream().map(ActionLoaderMapper::mapConditionFromConditionLoaderDTO).collect(Collectors.toList());
    }

    public static Condition mapConditionFromConditionLoaderDTO(ConditionLoaderDTO dto) {
        if (Objects.isNull(dto.getConditionName()))
            throw new ActionException(ActionException.ActionExceptionType.CONDITION_VALIDATION, "conditionName", "cannot be null");
        return switch (dto.getConditionName()) {
            case ActiveSynergyCondition.CONDITION_NAME -> new ActiveSynergyCondition(dto);
            case AttackAfterCondition.CONDITION_NAME -> new AttackAfterCondition(dto);
            case AttackBeforeCondition.CONDITION_NAME -> new AttackBeforeCondition(dto);
            case DetectSupportEffect.CONDITION_NAME -> new DetectSupportEffect(dto);
            case ExperienceReachedCondition.CONDITION_NAME -> new ExperienceReachedCondition(dto);
            case HaveTypeTwoCondition.CONDITION_NAME -> new HaveTypeTwoCondition(dto);
            case MultiPercentageCondition.CONDITION_NAME -> new MultiPercentageCondition(dto);
            case PercentageCondition.CONDITION_NAME -> new PercentageCondition(dto);
            default -> throw new ActionException(ActionException.ActionExceptionType.CONDITION_VALIDATION, dto.getConditionName(), "condition unknown");
        };
    }

    public static List<Target> mapTargetListFromStringList(List<String> stringList) {
        if (Objects.isNull(stringList)) {
            return new ArrayList<>();
        }
        return stringList.stream().map(Target::getEnum)
                .filter(Objects::nonNull).collect(Collectors.toList());
    }
}
