package com.pokechess.server.models.globals.game.effects;

public interface ValueEffect extends Effect {
    Integer getValue();
    void setValue(Integer value);
}
