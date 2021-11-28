package com.pokechess.server.datasources.database.card.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokechess.server.datasources.database.card.entity.ConditionEntity;
import com.pokechess.server.exceptions.ApiException;
import com.pokechess.server.models.globals.game.conditions.Condition;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

public class CardEntityMapper {
    public static List<ConditionEntity> mapConditionListToConditionEntityList(List<Condition> modelList) {
        return modelList.stream().map(Condition::mapToEntity)
                .collect(Collectors.toList());
    }

    public static String mapObjectToString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during json mapping");
        }
    }
}
