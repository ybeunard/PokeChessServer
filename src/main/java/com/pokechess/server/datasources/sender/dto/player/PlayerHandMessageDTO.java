package com.pokechess.server.datasources.sender.dto.player;

import com.pokechess.server.controllers.pokemon.dto.PokemonDTO;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public class PlayerHandMessageDTO {
    private List<PokemonDTO> pokemonHand;

    public List<PokemonDTO> getPokemonHand() {
        return pokemonHand;
    }

    public void setPokemonHand(List<PokemonDTO> pokemonHand) {
        this.pokemonHand = pokemonHand;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PlayerHandMessageDTO && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "PlayerHandMessageDTO [pokemonHand=%s]", this.pokemonHand);
    }
}
