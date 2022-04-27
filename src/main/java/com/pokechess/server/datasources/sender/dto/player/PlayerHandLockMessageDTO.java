package com.pokechess.server.datasources.sender.dto.player;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class PlayerHandLockMessageDTO {
    private Boolean handLock;

    public Boolean getHandLock() {
        return handLock;
    }

    public void setHandLock(Boolean handLock) {
        this.handLock = handLock;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PlayerHandLockMessageDTO && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "PlayerHandLockMessageDTO [handLock=%s]", this.handLock);
    }
}
