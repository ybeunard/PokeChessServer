package com.pokechess.server.controllers.security.dto;

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
}
