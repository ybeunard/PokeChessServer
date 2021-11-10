package com.pokechess.server.datasources.loader.dto;

import com.pokechess.server.datasources.loader.dto.actions.EffectDTO;
import org.springframework.lang.Nullable;

import java.util.List;

public class PokemonTrainerDTO {
    private Integer id;
    private List<EffectDTO> effects;

    @Nullable
    public Integer getId() {
        return id;
    }

    @Nullable
    public List<EffectDTO> getEffects() {
        return effects;
    }
}
