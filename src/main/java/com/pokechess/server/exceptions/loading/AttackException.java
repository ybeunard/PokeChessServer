package com.pokechess.server.exceptions.loading;

import com.pokechess.server.exceptions.ApiException;
import com.pokechess.server.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

public class AttackException extends ApiException {
    private static final String DEFAULT_ATTACK_NO_TARGET_MESSAGE = "Attack %s : Attack with power need minimum one target";
    private static final String DEFAULT_ATTACK_TARGET_POKEMON_MESSAGE = "Attack %s : Attack need target only pokemon";
    private static final String DEFAULT_ATTACK_EFFECT_POKEMON_MESSAGE = "Attack %s : effect %s have no apply when property";
    private static final String DEFAULT_ATTACK_VALIDATION_MESSAGE = "Attack %s : %s";

    public enum AttackExceptionType { ATTACK_NO_TARGET, ATTACK_TARGET_POKEMON, ATTACK_EFFECT_POKEMON, ATTACK_VALIDATION }

    private final AttackExceptionType type;
    private final String name;
    private String message;

    public static AttackException of(@NonNull AttackExceptionType type, String name) {
        return new AttackException(type, name);
    }

    public static AttackException of(@NonNull AttackExceptionType type, String name, String message) {
        return new AttackException(type, name, message);
    }

    public static AttackException of(String name,@NonNull ValidationException e) {
        return new AttackException(AttackExceptionType.ATTACK_VALIDATION, name, e.getMessage());
    }

    public static AttackException of(String name,@NonNull ActionException e) {
        return new AttackException(AttackExceptionType.ATTACK_VALIDATION, name, e.getMessage());
    }

    private AttackException(AttackExceptionType type, String name) {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
        this.type = type;
        this.name = name;
    }

    private AttackException(AttackExceptionType type, String name, String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
        this.type = type;
        this.name = name;
        this.message = message;
    }

    public AttackExceptionType getType() {
        return type;
    }

    @Override
    public String getMessage() {
        return switch (type) {
            case ATTACK_NO_TARGET -> String.format(DEFAULT_ATTACK_NO_TARGET_MESSAGE, this.name);
            case ATTACK_TARGET_POKEMON -> String.format(DEFAULT_ATTACK_TARGET_POKEMON_MESSAGE, this.name);
            case ATTACK_EFFECT_POKEMON -> String.format(DEFAULT_ATTACK_EFFECT_POKEMON_MESSAGE, this.name, this.message);
            case ATTACK_VALIDATION -> String.format(DEFAULT_ATTACK_VALIDATION_MESSAGE, this.name, this.message);
        };
    }
}
