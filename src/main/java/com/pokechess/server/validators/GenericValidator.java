package com.pokechess.server.validators;

import com.pokechess.server.exceptions.ValidationException;

import java.util.Collection;
import java.util.Objects;

public class GenericValidator {
    private static final String GENERIC_VALIDATOR_NOT_NULL_OBJECT = "cannot be null";
    private static final String GENERIC_VALIDATOR_NOT_EMPTY_STRING = "cannot be empty";
    private static final String GENERIC_VALIDATOR_NOT_EMPTY_LIST = "cannot be empty";
    private static final String GENERIC_VALIDATOR_MIN_INTEGER = "cannot be less than %s";
    private static final String GENERIC_VALIDATOR_MAX_STRING = "length cannot exceed %s";
    private static final String GENERIC_VALIDATOR_MAX_INTEGER = "cannot exceed %s";
    private static final String GENERIC_VALIDATOR_MULTIPLE_INTEGER = "must be a multiple of %s";
    private static final String GENERIC_VALIDATOR_PATTERN_STRING = "need to respect %s pattern";

    public static void notNull(Object obj, String fieldName) {
        if (Objects.isNull(obj)) {
            throw new ValidationException(fieldName, GENERIC_VALIDATOR_NOT_NULL_OBJECT);
        }
    }

    public static void notEmpty(String string, String fieldName) {
        if (Objects.isNull(string) || "".equals(string)) {
            throw new ValidationException(fieldName, GENERIC_VALIDATOR_NOT_EMPTY_STRING);
        }
    }

    public static void notEmpty(Collection<?> collection, String fieldName) {
        if (Objects.isNull(collection) || collection.isEmpty()) {
            throw new ValidationException(fieldName, GENERIC_VALIDATOR_NOT_EMPTY_LIST);
        }
    }

    public static void min(Integer integer, int min, String fieldName) {
        if (Objects.nonNull(integer) && integer < min) {
            throw new ValidationException(fieldName, integer.toString(), String.format(GENERIC_VALIDATOR_MIN_INTEGER, min));
        }
    }

    public static void max(String string, int max, String fieldName) {
        if (Objects.nonNull(string) && string.length() > max) {
            throw new ValidationException(fieldName, string, String.format(GENERIC_VALIDATOR_MAX_STRING, max));
        }
    }

    public static void max(Integer integer, int max, String fieldName) {
        if (Objects.nonNull(integer) && integer > max) {
            throw new ValidationException(fieldName, integer.toString(), String.format(GENERIC_VALIDATOR_MAX_INTEGER, max));
        }
    }

    public static void multiple(Integer integer, int multiple, String fieldName) {
        if (Objects.nonNull(integer) && integer % multiple != 0) {
            throw new ValidationException(fieldName, integer.toString(), String.format(GENERIC_VALIDATOR_MULTIPLE_INTEGER, multiple));
        }
    }

    public static void pattern(String string, String pattern, String fieldName) {
        if (Objects.nonNull(string) && !string.matches(pattern)) {
            throw new ValidationException(fieldName, string, String.format(GENERIC_VALIDATOR_PATTERN_STRING, pattern));
        }
    }
}
