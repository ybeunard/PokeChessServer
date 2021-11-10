package com.pokechess.server.controllers.security.dto;

public class UserRegisterDTO {
    private String username;
    private String password;
    private String trainerName;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getTrainerName() {
        return trainerName;
    }
}
