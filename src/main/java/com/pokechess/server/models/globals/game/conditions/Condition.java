package com.pokechess.server.models.globals.game.conditions;

import com.pokechess.server.datasources.database.card.entity.ConditionEntity;

public interface Condition {
    ConditionEntity mapToEntity();
    String getConditionName();
}
