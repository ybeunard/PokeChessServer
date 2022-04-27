package com.pokechess.server.controllers.party.dto.party.board.game;

import com.pokechess.server.controllers.pokemon.dto.PokemonDTO;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class PokemonInstanceDTO {
    private PokemonDTO pokemon;

    public PokemonDTO getPokemon() {
        return pokemon;
    }

    public void setPokemon(PokemonDTO pokemon) {
        this.pokemon = pokemon;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PokemonInstanceDTO && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "PokemonInstanceDTO [pokemon=%s]", this.pokemon);
    }
}
