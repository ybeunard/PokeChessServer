package com.pokechess.server.datasources.sender.dto.party;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class PartyUpdatePlayerGoldMessageDTO {
    private String trainerName;
    private Integer goldLevel;

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public Integer getGoldLevel() {
        return goldLevel;
    }

    public void setGoldLevel(Integer goldLevel) {
        this.goldLevel = goldLevel;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PartyUpdatePlayerGoldMessageDTO && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "PartyUpdatePlayerGoldMessageDTO [trainerName=%s, goldLevel=%s]", this.trainerName, this.goldLevel);
    }
}
