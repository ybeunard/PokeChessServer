package com.pokechess.server.models.globals.game.effects;

import com.pokechess.server.models.enumerations.actions.Target;

import java.util.List;

public interface TargetEffect extends Effect {
    List<Target> getTargets();
    void setTargets(List<Target> targets);
}
