package com.pokechess.server.controllers.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public String getTokenType() {
        return tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
