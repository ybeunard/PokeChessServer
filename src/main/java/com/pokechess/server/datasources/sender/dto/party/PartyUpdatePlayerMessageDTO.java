package com.pokechess.server.datasources.sender.dto.party;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public class PartyUpdatePlayerMessageDTO {
    public List<String> players;

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PartyUpdatePlayerMessageDTO && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "PartyUpdatePlayerMessageDTO [players=%s]", this.players);
    }
}
