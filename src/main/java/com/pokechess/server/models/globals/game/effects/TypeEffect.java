package com.pokechess.server.models.globals.game.effects;

import com.pokechess.server.models.enumerations.Type;

public interface TypeEffect extends Effect {
    Type getType();
    void setType(Type type);
}
