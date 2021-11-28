package com.pokechess.server.datasources.loader.dto.actions;

import com.pokechess.server.datasources.loader.dto.PokemonLoaderDTO;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public class EvolutionLoaderDTO {
    private PokemonLoaderDTO pokemon;
    private String description;
    private List<ConditionLoaderDTO> conditions;

    public PokemonLoaderDTO getPokemon() {
        return pokemon;
    }

    public String getDescription() {
        return description;
    }

    public List<ConditionLoaderDTO> getConditions() {
        return conditions;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof EvolutionLoaderDTO && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "EvolutionDTO [pokemon=%s, description=%s, conditions=%s]", this.pokemon, this.description, this.conditions);
    }
}
