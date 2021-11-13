package com.pokechess.server.controllers.security.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class JwtRequestDTO {
    private String username;
    private String password;

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

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof JwtRequestDTO && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public String toString() {
        return String.format(
                "JwtRequestDTO [username=%s, password=%s]", this.username, this.password);
    }
}
