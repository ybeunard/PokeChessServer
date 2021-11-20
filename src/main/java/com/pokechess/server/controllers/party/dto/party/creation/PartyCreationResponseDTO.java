package com.pokechess.server.controllers.party.dto.party.creation;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public class PartyCreationResponseDTO {
    private String owner;
    private String name;
    private List<String> players;
    private String state;
    private Boolean withPassword;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Boolean getWithPassword() {
        return withPassword;
    }

    public void setWithPassword(Boolean withPassword) {
        this.withPassword = withPassword;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PartyCreationResponseDTO && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "PartyCreationResponseDTO [owner=%s, name=%s, players=%s, state=%s, withPassword=%s]", this.owner, this.name, this.players, this.state, this.withPassword);
    }
}
