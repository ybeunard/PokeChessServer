package com.pokechess.server.controllers.party.dto.party.creation;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PartyCreationRequestDTO {
    @NotEmpty(message = "field cannot be empty")
    @Size(max = 50, message = "cannot exceeded 50 characters")
    private String name;
    @Size(max = 50, message = "cannot exceeded 50 characters")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PartyCreationRequestDTO && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "PartyCreationRequestDTO [name=%s, password=%s]", this.name, this.password);
    }
}
