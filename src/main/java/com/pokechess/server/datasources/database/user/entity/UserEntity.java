package com.pokechess.server.datasources.database.user.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Entity(name = "user_entity")
public class UserEntity {
    private Integer id;
    private String username;
    private String passwordHashed;
    private String trainerName;
    private String accessTokenId;
    private String refreshTokenId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(unique = true, nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password_hashed", nullable = false)
    public String getPasswordHashed() {
        return passwordHashed;
    }

    public void setPasswordHashed(String passwordHashed) {
        this.passwordHashed = passwordHashed;
    }

    @Column(name = "trainer_name", unique = true, nullable = false)
    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    @Column(name = "access_token_id")
    public String getAccessTokenId() {
        return accessTokenId;
    }

    public void setAccessTokenId(String accessTokenId) {
        this.accessTokenId = accessTokenId;
    }

    @Column(name = "refresh_token_id")
    public String getRefreshTokenId() {
        return refreshTokenId;
    }

    public void setRefreshTokenId(String refreshTokenId) {
        this.refreshTokenId = refreshTokenId;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof UserEntity && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "UserEntity [id=%s, username=%s, passwordHashed=%s, trainerName=%s, accessTokenId=%s, refreshTokenId=%s]", this.id, this.username, this.passwordHashed, this.trainerName, this.accessTokenId, this.refreshTokenId);
    }
}
