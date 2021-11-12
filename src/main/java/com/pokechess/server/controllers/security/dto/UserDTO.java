package com.pokechess.server.controllers.security.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.lang.Nullable;

public class UserDTO {
    private String username;
    private String trainerName;

    @Nullable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Nullable
    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof UserDTO && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "UserDTO [username=%s, trainerName=%s]", this.username, this.trainerName);
    }
}
