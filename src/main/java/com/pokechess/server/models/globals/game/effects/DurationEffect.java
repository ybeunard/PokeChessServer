package com.pokechess.server.models.globals.game.effects;

import com.pokechess.server.models.enumerations.actions.DurationTime;

public interface DurationEffect extends Effect {
    DurationTime getDuration();
    void setDuration(DurationTime duration);
}
