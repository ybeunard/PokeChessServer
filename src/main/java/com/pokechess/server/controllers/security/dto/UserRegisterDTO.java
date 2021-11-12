package com.pokechess.server.controllers.security.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserRegisterDTO {
    @NotEmpty(message = "field cannot be empty")
    @Size(max = 50, message = "cannot exceeded 50 characters")
    private String username;
    @NotEmpty(message = "field cannot be empty")
    @Size(max = 50, message = "cannot exceeded 50 characters")
    private String password;
    @NotEmpty(message = "field cannot be empty")
    @Size(max = 50, message = "cannot exceeded 50 characters")
    private String trainerName;

    @Nullable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Nullable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        return o instanceof UserRegisterDTO && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(username).append(password).append(trainerName).toHashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "UserRegisterDTO [username=%s, password=%s, trainerName=%s]", this.username, this.password, this.trainerName);
    }
}
