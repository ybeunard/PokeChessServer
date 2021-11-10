package com.pokechess.server.exceptions;

import org.springframework.http.HttpStatus;

public class JwtException extends ApiException {
    private static final String DEFAULT_BAD_CREDENTIALS_MESSAGE = "Bad credentials";
    public static final String DEFAULT_INCORRECT_JWT_TOKEN_MESSAGE = "Incorrect JWT token";
    private static final String DEFAULT_EXPIRED_JWT_TOKEN_MESSAGE = "Expired JWT token";

    public enum JwtExceptionType { BAD_CREDENTIALS, INCORRECT_JWT_TOKEN, EXPIRED_JWT_TOKEN }

    private final JwtExceptionType type;

    public static JwtException of(JwtExceptionType type) {
        return switch (type) {
            case BAD_CREDENTIALS, INCORRECT_JWT_TOKEN, EXPIRED_JWT_TOKEN -> new JwtException(HttpStatus.UNAUTHORIZED, type);
        };
    }

    private JwtException(HttpStatus status, JwtExceptionType type) {
        super(status);
        this.type = type;
    }

    public JwtExceptionType getType() {
        return type;
    }

    public String getMessage() {
        return switch (type) {
            case BAD_CREDENTIALS -> DEFAULT_BAD_CREDENTIALS_MESSAGE;
            case INCORRECT_JWT_TOKEN -> DEFAULT_INCORRECT_JWT_TOKEN_MESSAGE;
            case EXPIRED_JWT_TOKEN -> DEFAULT_EXPIRED_JWT_TOKEN_MESSAGE;
        };
    }
}
