package com.pokechess.server.models.globals.game.effects;

import com.pokechess.server.models.enumerations.PokemonStatus;

public interface StatusEffect extends Effect {
    PokemonStatus getStatus();
    void setStatus(PokemonStatus status);
}
