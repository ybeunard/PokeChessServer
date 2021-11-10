package com.pokechess.server.validators;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class GenericValidator {
    public static void notNull(Object obj) {
        if (Objects.isNull(obj)) {
            // TODO THROW
        }
    }

    public static void notEmpty(String string) {
        if (Objects.isNull(string) || "".equals(string)) {
            // TODO THROW
        }
    }

    public static void notEmpty(Collection<?> list) {
        if (Objects.isNull(list) || list.isEmpty()) {
            // TODO THROW
        }
    }
}
