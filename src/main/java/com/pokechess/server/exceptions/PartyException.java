package com.pokechess.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

public class PartyException extends ApiException {
    private static final String DEFAULT_PARTY_MAX_PLAYER_MESSAGE = "Party max player already reached";
    private static final String DEFAULT_PARTY_MIN_PLAYER_MESSAGE = "Party min player not reached";
    private static final String DEFAULT_NAME_ALREADY_EXIST_MESSAGE = "Party name already exist";
    private static final String DEFAULT_INCORRECT_PASSWORD_MESSAGE = "Party password incorrect";
    private static final String DEFAULT_PARTY_NOT_FOUND_MESSAGE = "Party not found";
    private static final String DEFAULT_PARTY_ALREADY_START_MESSAGE = "Party already started";
    private static final String DEFAULT_PARTY_NOT_START_MESSAGE = "Party not started";
    private static final String DEFAULT_INCORRECT_STATE_MESSAGE = "Party state incorrect";

    public enum PartyExceptionType { PARTY_NOT_FOUND, NAME_ALREADY_EXIST, INCORRECT_PASSWORD, PARTY_MIN_PLAYER, PARTY_MAX_PLAYER, PARTY_ALREADY_START,
        PARTY_NOT_START, INCORRECT_STATE }

    private final PartyExceptionType type;

    public static PartyException of(@NonNull PartyExceptionType type) {
        return switch (type) {
            case PARTY_NOT_START, INCORRECT_STATE, PARTY_MIN_PLAYER -> new PartyException(HttpStatus.BAD_REQUEST, type);
            case PARTY_MAX_PLAYER, INCORRECT_PASSWORD, PARTY_ALREADY_START -> new PartyException(HttpStatus.FORBIDDEN, type);
            case PARTY_NOT_FOUND -> new PartyException(HttpStatus.NOT_FOUND, DEFAULT_PARTY_NOT_FOUND_MESSAGE, type);
            case NAME_ALREADY_EXIST -> new PartyException(HttpStatus.CONFLICT, type);
        };
    }

    private PartyException(HttpStatus status, PartyExceptionType type) {
        super(status);
        this.type = type;
    }

    private PartyException(HttpStatus status, String message, PartyExceptionType type) {
        super(status, message);
        this.type = type;
    }

    public PartyExceptionType getType() {
        return type;
    }

    public String getMessage() {
        return switch (type) {
            case NAME_ALREADY_EXIST -> DEFAULT_NAME_ALREADY_EXIST_MESSAGE;
            case INCORRECT_PASSWORD -> DEFAULT_INCORRECT_PASSWORD_MESSAGE;
            case PARTY_ALREADY_START -> DEFAULT_PARTY_ALREADY_START_MESSAGE;
            case PARTY_MIN_PLAYER -> DEFAULT_PARTY_MIN_PLAYER_MESSAGE;
            case PARTY_MAX_PLAYER -> DEFAULT_PARTY_MAX_PLAYER_MESSAGE;
            case PARTY_NOT_START -> DEFAULT_PARTY_NOT_START_MESSAGE;
            case INCORRECT_STATE -> DEFAULT_INCORRECT_STATE_MESSAGE;
            default -> super.getMessage();
        };
    }
}
