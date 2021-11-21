package com.pokechess.server.controllers.party.dto.party.list;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public class PartyListInCreationResponseDTO {
    private List<PartyInCreationDTO> parties;

    public List<PartyInCreationDTO> getParties() {
        return parties;
    }

    public void setParties(List<PartyInCreationDTO> parties) {
        this.parties = parties;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PartyListInCreationResponseDTO && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "PartyListInCreationResponseDTO [parties=%s]", this.parties);
    }
}
