package com.pokechess.server.datasources.sender.dto.party;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public class PartyUpdatePlayerConnectionMessageDTO {
    public List<String> players;
    public List<String> playersDisconnected;

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public List<String> getPlayersDisconnected() {
        return playersDisconnected;
    }

    public void setPlayersDisconnected(List<String> playersDisconnected) {
        this.playersDisconnected = playersDisconnected;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PartyUpdatePlayerConnectionMessageDTO && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "PartyUpdatePlayerConnectionMessageDTO [players=%s, playersDisconnected=%s]", this.players, this.playersDisconnected);
    }
}
