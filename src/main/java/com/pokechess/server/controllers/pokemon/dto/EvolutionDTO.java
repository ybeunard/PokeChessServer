package com.pokechess.server.controllers.pokemon.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EvolutionDTO {
    public static final String DEFAULT_EVOLUTION_TYPE = "Evolution";

    private String evolutionType;
    private String description;
    private String pokemonId;
    private String name;

    public String getEvolutionType() {
        return evolutionType;
    }

    public void setEvolutionType(String evolutionType) {
        this.evolutionType = evolutionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPokemonId() {
        return pokemonId;
    }

    public void setPokemonId(String pokemonId) {
        this.pokemonId = pokemonId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof EvolutionDTO && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "EvolutionDTO [evolutionType=%s, description=%s, pokemonId=%s, name=%s]", this.evolutionType, this.description, this.pokemonId, this.name);
    }
}
