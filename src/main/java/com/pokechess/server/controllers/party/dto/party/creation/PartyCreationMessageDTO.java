package com.pokechess.server.controllers.party.dto.party.creation;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class PartyCreationMessageDTO {
    private String name;
    private String owner;
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

    public Boolean getWithPassword() {
        return withPassword;
    }

    public void setWithPassword(Boolean withPassword) {
        this.withPassword = withPassword;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PartyCreationMessageDTO && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "PartyCreationMessageDTO [name=%s, owner=%s, withPassword=%s]", this.name, this.owner, this.withPassword);
    }
}
