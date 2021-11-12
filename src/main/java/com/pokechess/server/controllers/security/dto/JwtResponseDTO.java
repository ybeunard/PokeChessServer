package com.pokechess.server.controllers.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class JwtResponseDTO {
    private static final String TOKEN_TYPE = "bearer";

    @JsonProperty("token_type")
    private final String tokenType;

    @JsonProperty("access_token")
    private final String accessToken;

    @JsonProperty("expiresIn")
    private final long expiresIn;

    @JsonProperty("refresh_token")
    private final String refreshToken;

    public JwtResponseDTO(String accessToken, long expiresIn, String refreshToken) {
        this.tokenType = TOKEN_TYPE;
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
    }

    @NonNull
    public String getTokenType() {
        return tokenType;
    }

    @Nullable
    public String getAccessToken() {
        return accessToken;
    }

    @NonNull
    public long getExpiresIn() {
        return expiresIn;
    }

    @Nullable
    public String getRefreshToken() {
        return refreshToken;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof JwtResponseDTO && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "JwtResponseDTO [tokenType=%s, accessToken=%s, expiresIn=%s, refreshToken=%s]", this.tokenType, this.accessToken, this.expiresIn, this.refreshToken);
    }
}
