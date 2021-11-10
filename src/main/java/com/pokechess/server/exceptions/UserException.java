package com.pokechess.server.exceptions;

import org.springframework.http.HttpStatus;

public class UserException extends ApiException {
    private static final String DEFAULT_USER_NOT_FOUND_MESSAGE = "User not found";
    private static final String DEFAULT_USERNAME_ALREADY_EXIST_MESSAGE = "Username already exist";
    private static final String DEFAULT_TRAINER_NAME_ALREADY_EXIST_MESSAGE = "Trainer name already exist";

    public enum UserExceptionType { USER_NOT_FOUND, USERNAME_ALREADY_EXIST, TRAINER_NAME_ALREADY_EXIST }

    private final UserExceptionType type;

    public static UserException of(UserExceptionType type) {
        return switch (type) {
            case USER_NOT_FOUND -> new UserException(HttpStatus.NOT_FOUND, DEFAULT_USER_NOT_FOUND_MESSAGE, type);
            case USERNAME_ALREADY_EXIST, TRAINER_NAME_ALREADY_EXIST -> new UserException(HttpStatus.CONFLICT, type);
        };
    }

    private UserException(HttpStatus status, UserExceptionType type) {
        super(status);
        this.type = type;
    }

    private UserException(HttpStatus status, String message, UserExceptionType type) {
        super(status, message);
        this.type = type;
    }

    public UserExceptionType getType() {
        return type;
    }

    public String getMessage() {
        return switch (type) {
            case USERNAME_ALREADY_EXIST -> DEFAULT_USERNAME_ALREADY_EXIST_MESSAGE;
            case TRAINER_NAME_ALREADY_EXIST -> DEFAULT_TRAINER_NAME_ALREADY_EXIST_MESSAGE;
            default -> super.getMessage();
        };
    }
}
