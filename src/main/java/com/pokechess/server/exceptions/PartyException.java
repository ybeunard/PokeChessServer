package com.pokechess.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

public class PartyException extends ApiException {
    private static final String DEFAULT_PARTY_NOT_FOUND_MESSAGE = "Party not found";

    public enum PartyExceptionType { PARTY_NOT_FOUND }

    private final PartyExceptionType type;

    public static PartyException of(@NonNull PartyExceptionType type) {
        return new PartyException(HttpStatus.NOT_FOUND, DEFAULT_PARTY_NOT_FOUND_MESSAGE, type);
    }

    private PartyException(HttpStatus status, String message, PartyExceptionType type) {
        super(status, message);
        this.type = type;
    }

    public PartyExceptionType getType() {
        return type;
    }
}
