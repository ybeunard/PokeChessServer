package com.pokechess.server.controllers.game.dto.message;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class MoveMessageDTO {
    private String id;
    private String place;
    private Integer position;
    private String destinationPlace;
    private Integer destinationPosition;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getDestinationPlace() {
        return destinationPlace;
    }

    public void setDestinationPlace(String destinationPlace) {
        this.destinationPlace = destinationPlace;
    }

    public Integer getDestinationPosition() {
        return destinationPosition;
    }

    public void setDestinationPosition(Integer destinationPosition) {
        this.destinationPosition = destinationPosition;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof MoveMessageDTO && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "MoveMessageDTO [id=%s, place=%s, position=%s, destinationPlace=%s, destinationPosition=%s]", this.id, this.place, this.position, this.destinationPlace, this.destinationPosition);
    }
}
