package com.pokechess.server.datasources.loader.mapper;

import com.pokechess.server.datasources.loader.dto.actions.ConditionDTO;
import com.pokechess.server.datasources.loader.dto.actions.EffectDTO;
import com.pokechess.server.datasources.loader.dto.actions.TriggerDTO;
import com.pokechess.server.models.enumerations.PokemonStatus;
import com.pokechess.server.models.enumerations.Synergy;
import com.pokechess.server.models.enumerations.Type;
import com.pokechess.server.models.enumerations.actions.*;
import com.pokechess.server.models.globals.game.actions.Condition;
import com.pokechess.server.models.globals.game.actions.Effect;
import com.pokechess.server.models.globals.game.actions.Trigger;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ActionMapper {

    public static List<Effect> mapEffectListFromEffectDTOList(List<EffectDTO> dtoList) {
        return dtoList.stream().map(ActionMapper::mapEffectFromEffectDTO).collect(Collectors.toList());
    }

    public static Effect mapEffectFromEffectDTO(EffectDTO dto) {
        Effect.EffectBuilder builder = Effect.builder().type(EffectType.getEnum(dto.getType()))
                .level(dto.getLevel()).status(PokemonStatus.getEnum(dto.getStatus()))
                .typePokemon(Type.getEnum(dto.getTypePokemon())).duration(DurationTime.getEnum(dto.getDuration()))
                .levels(dto.getLevels());

        Optional.ofNullable(dto.getTrigger()).map(ActionMapper::mapTriggerFromTriggerDTO)
                .ifPresent(builder::trigger);
        Optional.ofNullable(dto.getConditions()).map(ActionMapper::mapConditionListFromConditionDTOList)
                .ifPresent(builder::conditions);
        Optional.ofNullable(dto.getTargets()).map(ActionMapper::mapTargetListFromStringList)
                .ifPresent(builder::targets);
        return builder.build();
    }

    public static Trigger mapTriggerFromTriggerDTO(TriggerDTO dto) {
        Trigger.TriggerBuilder builder = Trigger.builder().action(TriggerAction.getEnum(dto.getAction()))
                .status(PokemonStatus.getEnum(dto.getStatus())).duration(DurationTime.getEnum(dto.getDuration()));
        Optional.ofNullable(dto.getTargets()).map(ActionMapper::mapTargetListFromStringList)
                .ifPresent(builder::targets);
        return builder.build();
    }

    public static List<Condition> mapConditionListFromConditionDTOList(List<ConditionDTO> dtoList) {
        return dtoList.stream().map(ActionMapper::mapConditionFromConditionDTO).collect(Collectors.toList());
    }

    public static Condition mapConditionFromConditionDTO(ConditionDTO dto) {
        return Condition.builder().type(ConditionType.getEnum(dto.getType()))
                .target(Target.getEnum(dto.getTarget())).level(dto.getLevel())
                .synergy(Synergy.getEnum(dto.getSynergy())).typePokemon(Type.getEnum(dto.getTypePokemon()))
                .successRate(dto.getSuccessRate()).build();
    }

    public static List<Type> mapTypeListFromStringList(List<String> stringList) {
        return stringList.stream().map(Type::getEnum).collect(Collectors.toList());
    }

    public static List<Target> mapTargetListFromStringList(List<String> stringList) {
        return stringList.stream().map(Target::getEnum).collect(Collectors.toList());
    }
}
