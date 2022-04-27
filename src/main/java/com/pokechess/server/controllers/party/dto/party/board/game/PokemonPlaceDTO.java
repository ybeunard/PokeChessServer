package com.pokechess.server.controllers.party.dto.party.board.game;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class PokemonPlaceDTO {
    private Integer position;
    private PokemonInstanceDTO pokemon;

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public PokemonInstanceDTO getPokemon() {
        return pokemon;
    }

    public void setPokemon(PokemonInstanceDTO pokemon) {
        this.pokemon = pokemon;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PokemonPlaceDTO && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "PokemonPlaceDTO [position=%s, pokemon=%s]", this.position, this.pokemon);
    }
}
