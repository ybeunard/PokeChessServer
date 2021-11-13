package com.pokechess.server.exceptions;

import org.springframework.http.HttpStatus;

public class ValidationException extends ApiException {
    private static final String NO_VALUE = "";

    private final String fieldName;
    private final String fieldValue;
    private final String message;

    public ValidationException(String fieldName, String message) {
        super(HttpStatus.BAD_REQUEST);
        this.fieldName = fieldName;
        this.fieldValue = NO_VALUE;
        this.message = message;
    }

    public ValidationException(String fieldName, String fieldValue, String message) {
        super(HttpStatus.BAD_REQUEST);
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.message = message;
    }

    public String getMessage() {
        if (NO_VALUE.equals(fieldValue)) {
            return String.format("%s : %s", fieldName, message);
        }
        return String.format("%s %s : %s", fieldName, fieldValue, message);
    }
}
