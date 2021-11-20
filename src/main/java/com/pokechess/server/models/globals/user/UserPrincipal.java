package com.pokechess.server.models.globals.user;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;

public class UserPrincipal extends User {
    private final String trainerName;

    private UserPrincipal(String username, String password, String trainerName) {
        super(username, password, new ArrayList<>());
        this.trainerName = trainerName;
    }

    public static UserPrincipalBuilder getBuilder() {
        return new UserPrincipalBuilder();
    }

    public static class UserPrincipalBuilder {
        private String username;
        private String password;
        private String trainerName;

        public UserPrincipalBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserPrincipalBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserPrincipalBuilder trainerName(String trainerName) {
            this.trainerName = trainerName;
            return this;
        }

        public UserPrincipal build() {
            return new UserPrincipal(username, password, trainerName);
        }
    }

    public String getTrainerName() {
        return trainerName;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof UserPrincipal && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "UserPrincipal [trainerName=%s]", this.trainerName);
    }
}
