package com.pokechess.server.models.globals;

import com.pokechess.server.validators.GenericValidator;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class User {
    private Integer id;
    private String username;
    private String passwordHashed;
    private String trainerName;
    private String accessTokenId;
    private String refreshTokenId;

    private User() { }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private Integer id;
        private String username;
        private String passwordHashed;
        private String trainerName;
        private String accessTokenId;
        private String refreshTokenId;

        public UserBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder passwordHashed(String passwordHashed) {
            this.passwordHashed = passwordHashed;
            return this;
        }

        public UserBuilder trainerName(String trainerName) {
            this.trainerName = trainerName;
            return this;
        }

        public UserBuilder accessTokenId(String accessTokenId) {
            this.accessTokenId = accessTokenId;
            return this;
        }

        public UserBuilder refreshTokenId(String refreshTokenId) {
            this.refreshTokenId = refreshTokenId;
            return this;
        }

        public User build() {
            User user = new User();
            user.setId(id);
            user.setUsername(username);
            user.setPasswordHashed(passwordHashed);
            user.setTrainerName(trainerName);
            user.setAccessTokenId(accessTokenId);
            user.setRefreshTokenId(refreshTokenId);
            return user;
        }
    }

    @Nullable
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        GenericValidator.notEmpty(username);
        this.username = username;
    }

    @NonNull
    public String getPasswordHashed() {
        return passwordHashed;
    }

    public void setPasswordHashed(String passwordHashed) {
        GenericValidator.notEmpty(passwordHashed);
        this.passwordHashed = passwordHashed;
    }

    @NonNull
    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        GenericValidator.notEmpty(trainerName);
        this.trainerName = trainerName;
    }

    @Nullable
    public String getAccessTokenId() {
        return accessTokenId;
    }

    public void setAccessTokenId(String accessTokenId) {
        this.accessTokenId = accessTokenId;
    }

    @Nullable
    public String getRefreshTokenId() {
        return refreshTokenId;
    }

    public void setRefreshTokenId(String refreshTokenId) {
        this.refreshTokenId = refreshTokenId;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof User && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "User [id=%s, username=%s, passwordHashed=%s, trainerName=%s, accessTokenId=%s, refreshTokenId=%s]", this.id, this.username, this.passwordHashed, this.trainerName, this.accessTokenId, this.refreshTokenId);
    }
}
