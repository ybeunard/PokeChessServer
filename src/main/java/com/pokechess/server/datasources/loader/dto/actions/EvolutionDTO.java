package com.pokechess.server.datasources.loader.dto.actions;

import com.pokechess.server.datasources.loader.dto.PokemonDTO;
import org.springframework.lang.Nullable;

import java.util.List;

public class EvolutionDTO {
    private PokemonDTO pokemon;
    private List<ConditionDTO> conditions;

    @Nullable
    public PokemonDTO getPokemon() {
        return pokemon;
    }

    @Nullable
    public List<ConditionDTO> getConditions() {
        return conditions;
    }
}
