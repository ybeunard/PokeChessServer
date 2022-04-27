package com.pokechess.server.datasources.sender.dto.player;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class PlayerGoldMessageDTO {
    private Integer gold;

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PlayerGoldMessageDTO && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "PlayerGoldMessageDTO [gold=%s]", this.gold);
    }
}
