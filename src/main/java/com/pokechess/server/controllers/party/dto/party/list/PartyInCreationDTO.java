package com.pokechess.server.controllers.party.dto.party.list;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class PartyInCreationDTO {
    private String name;
    private String owner;
    private Integer numberOfPlayer;
    private Boolean withPassword;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getNumberOfPlayer() {
        return numberOfPlayer;
    }

    public void setNumberOfPlayer(Integer numberOfPlayer) {
        this.numberOfPlayer = numberOfPlayer;
    }

    public Boolean getWithPassword() {
        return withPassword;
    }

    public void setWithPassword(Boolean withPassword) {
        this.withPassword = withPassword;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PartyInCreationDTO && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "PartyInCreationDTO [name=%s, owner=%s, numberOfPlayer=%s, withPassword=%s]", this.name, this.owner, this.numberOfPlayer, this.withPassword);
    }
}
