package com.pokechess.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

public class ApiException extends ResponseStatusException {
    private static final String DEFAULT_BAD_REQUEST_MESSAGE = "Bad request";
    private static final String DEFAULT_UNAUTHORIZED_MESSAGE = "You cannot perform this action";
    private static final String DEFAULT_FORBIDDEN_MESSAGE = "You aren't authorize to perform this action";
    private static final String DEFAULT_NOT_FOUND_MESSAGE = "The resource wasn't found";

    private String message;

    public ApiException(HttpStatus status) {
        super(status);
    }

    public ApiException(HttpStatus status, String message) {
        super(status);
        this.message = message;
    }

    public String getMessage() {
        switch (getStatus()) {
            case BAD_REQUEST:
                return completeWithMessage(DEFAULT_BAD_REQUEST_MESSAGE);
            case UNAUTHORIZED:
                return completeWithMessage(DEFAULT_UNAUTHORIZED_MESSAGE);
            case FORBIDDEN:
                return completeWithMessage(DEFAULT_FORBIDDEN_MESSAGE);
            case NOT_FOUND:
                return completeWithMessage(DEFAULT_NOT_FOUND_MESSAGE);
            case INTERNAL_SERVER_ERROR:
                if (Objects.nonNull(message)) {
                    return "An error appeared during the request : " + message + ". Please retry later";
                }
                return "An error appeared during the request. Please retry later";
            default:
                if (Objects.nonNull(message)) {
                    return message;
                }
                return super.getMessage();
        }
    }

    private String completeWithMessage(String defaultMessage) {
        if (Objects.nonNull(message)) {
            return defaultMessage + " : " + message;
        }
        return defaultMessage;
    }
}
