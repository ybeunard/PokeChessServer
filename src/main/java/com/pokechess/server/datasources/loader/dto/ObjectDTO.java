package com.pokechess.server.datasources.loader.dto;

import com.pokechess.server.datasources.loader.dto.actions.EffectLoaderDTO;
import org.springframework.lang.Nullable;

import java.util.List;

public class ObjectDTO {
    private Integer id;
    private List<EffectLoaderDTO> combatEffects;
    private List<EffectLoaderDTO> outOfCombatEffects;

    @Nullable
    public Integer getId() {
        return id;
    }

    @Nullable
    public List<EffectLoaderDTO> getCombatEffects() {
        return combatEffects;
    }

    @Nullable
    public List<EffectLoaderDTO> getOutOfCombatEffects() {
        return outOfCombatEffects;
    }
}
