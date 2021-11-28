package com.pokechess.server.exceptions.loading;

import com.pokechess.server.exceptions.ApiException;
import com.pokechess.server.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;

public class ActionException extends ApiException {
    private static final String DEFAULT_EFFECT_VALIDATION_MESSAGE = "Effect %s : %s";
    private static final String DEFAULT_CONDITION_VALIDATION_MESSAGE = "Condition %s : %s";
    private static final String DEFAULT_EVOLUTION_VALIDATION_MESSAGE = "Evolution: %s";

    public enum ActionExceptionType { EFFECT_VALIDATION, CONDITION_VALIDATION, EVOLUTION_VALIDATION }

    private final ActionExceptionType type;
    private final String name;
    private final String message;

    public static ActionException of(@NotNull ActionExceptionType type, String name, @NonNull ValidationException e) {
        return new ActionException(type, name, e.getMessage());
    }

    public static ActionException of(@NotNull ActionExceptionType type, String name, @NonNull ActionException e) {
        return new ActionException(type, name, e.getMessage());
    }

    public ActionException(ActionExceptionType type, String name, String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
        this.type = type;
        this.name = name;
        this.message = message;
    }

    public ActionExceptionType getType() {
        return type;
    }

    public String getMessage() {
        return switch (type) {
            case EFFECT_VALIDATION -> String.format(DEFAULT_EFFECT_VALIDATION_MESSAGE, name, message);
            case CONDITION_VALIDATION -> String.format(DEFAULT_CONDITION_VALIDATION_MESSAGE, name, message);
            case EVOLUTION_VALIDATION -> String.format(DEFAULT_EVOLUTION_VALIDATION_MESSAGE,  message);
        };
    }
}
