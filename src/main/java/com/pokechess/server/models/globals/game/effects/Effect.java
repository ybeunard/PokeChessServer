package com.pokechess.server.models.globals.game.effects;


import com.pokechess.server.datasources.database.card.entity.EffectEntity;
import com.pokechess.server.models.globals.game.conditions.Condition;

import java.util.List;

public interface Effect {
    EffectEntity mapToEntity();
    String getEffectName();
    List<Condition> getConditions();
    void setConditions(List<Condition> conditions);
}