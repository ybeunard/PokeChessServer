package com.pokechess.server.controllers.security.dto.user.register;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRegisterRequestDTO {
    @NotEmpty(message = "field cannot be empty")
    @Size(max = 50, message = "cannot exceeded 50 characters")
    private String username;
    @NotEmpty(message = "field cannot be empty")
    @Size(max = 50, message = "cannot exceeded 50 characters")
    private String password;
    @NotEmpty(message = "field cannot be empty")
    @Size(max = 50, message = "cannot exceeded 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "can only contains letters, numbers and underscore")
    private String trainerName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof UserRegisterRequestDTO && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "UserRegisterDTO [username=%s, password=%s, trainerName=%s]", this.username, this.password, this.trainerName);
    }
}
