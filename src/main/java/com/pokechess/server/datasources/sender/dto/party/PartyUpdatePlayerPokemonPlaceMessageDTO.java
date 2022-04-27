package com.pokechess.server.datasources.sender.dto.party;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pokechess.server.controllers.party.dto.party.board.game.PokemonPlaceDTO;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartyUpdatePlayerPokemonPlaceMessageDTO {
    private String trainerName;
    private List<PokemonPlaceDTO> places;
    private List<PokemonPlaceDTO> placesOverload;

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public List<PokemonPlaceDTO> getPlaces() {
        return places;
    }

    public void setPlaces(List<PokemonPlaceDTO> place) {
        this.places = place;
    }

    public List<PokemonPlaceDTO> getPlacesOverload() {
        return placesOverload;
    }

    public void setPlacesOverload(List<PokemonPlaceDTO> placeOverload) {
        this.placesOverload = placeOverload;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PartyUpdatePlayerPokemonPlaceMessageDTO && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "PartyUpdatePlayerPokemonPlaceMessageDTO [trainerName=%s, places=%s, placesOverload=%s]", this.trainerName, this.places, this.placesOverload);
    }
}
