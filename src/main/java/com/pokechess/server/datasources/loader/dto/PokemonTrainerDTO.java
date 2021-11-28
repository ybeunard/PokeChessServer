package com.pokechess.server.datasources.loader.dto;

import com.pokechess.server.datasources.loader.dto.actions.EffectLoaderDTO;
import org.springframework.lang.Nullable;

import java.util.List;

public class PokemonTrainerDTO {
    private Integer id;
    private List<EffectLoaderDTO> effects;

    @Nullable
    public Integer getId() {
        return id;
    }

    @Nullable
    public List<EffectLoaderDTO> getEffects() {
        return effects;
    }
}
