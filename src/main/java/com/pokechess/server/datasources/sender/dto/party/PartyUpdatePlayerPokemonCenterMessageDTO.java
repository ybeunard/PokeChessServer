package com.pokechess.server.datasources.sender.dto.party;

import com.pokechess.server.controllers.party.dto.party.board.game.PokemonPlaceDTO;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class PartyUpdatePlayerPokemonCenterMessageDTO {
    private String trainerName;
    private PokemonPlaceDTO pokemonCenter;

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public PokemonPlaceDTO getPokemonCenter() {
        return pokemonCenter;
    }

    public void setPokemonCenter(PokemonPlaceDTO pokemonCenter) {
        this.pokemonCenter = pokemonCenter;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PartyUpdatePlayerPokemonCenterMessageDTO && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "PartyUpdatePlayerPokemonCenterMessageDTO [trainerName=%s, pokemonCenter=%s]", this.trainerName, this.pokemonCenter);
    }
}
