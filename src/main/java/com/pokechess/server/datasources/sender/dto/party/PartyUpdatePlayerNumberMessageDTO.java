package com.pokechess.server.datasources.sender.dto.party;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class PartyUpdatePlayerNumberMessageDTO {
    private String name;
    private Integer playerNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(Integer playerNumber) {
        this.playerNumber = playerNumber;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PartyUpdatePlayerNumberMessageDTO && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "PartyUpdatePlayerNumberMessageDTO [name=%s, playerNumber=%s]", this.name, this.playerNumber);
    }
}
