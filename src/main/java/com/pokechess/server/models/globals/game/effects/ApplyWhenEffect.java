package com.pokechess.server.models.globals.game.effects;

import com.pokechess.server.models.enumerations.actions.ApplyWhen;

public interface ApplyWhenEffect extends Effect {
    ApplyWhen getApplyWhen();
    void setApplyWhen(ApplyWhen applyWhen);
}
