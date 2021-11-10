package com.pokechess.server.datasources.loader.dto;

import com.pokechess.server.datasources.loader.dto.actions.EffectDTO;
import org.springframework.lang.Nullable;

import java.util.List;

public class ObjectDTO {
    private Integer id;
    private List<EffectDTO> combatEffects;
    private List<EffectDTO> outOfCombatEffects;

    @Nullable
    public Integer getId() {
        return id;
    }

    @Nullable
    public List<EffectDTO> getCombatEffects() {
        return combatEffects;
    }

    @Nullable
    public List<EffectDTO> getOutOfCombatEffects() {
        return outOfCombatEffects;
    }
}
