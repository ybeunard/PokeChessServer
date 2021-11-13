package com.pokechess.server.validators;

import com.pokechess.server.exceptions.ValidationException;

import java.util.Collection;
import java.util.Objects;

public class GenericValidator {
    private static final String GENERIC_VALIDATOR_NOT_NULL_OBJECT = "cannot be null";
    private static final String GENERIC_VALIDATOR_NOT_EMPTY_STRING = "cannot be empty";
    private static final String GENERIC_VALIDATOR_NOT_EMPTY_LIST = "cannot be empty";
    private static final String GENERIC_VALIDATOR_MAX_STRING = "length cannot exceed %s";

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

    public static void max(String string, int max, String fieldName) {
        if (Objects.nonNull(string) && string.length() > max) {
            throw new ValidationException(fieldName, string, String.format(GENERIC_VALIDATOR_MAX_STRING, max));
        }
    }
}
