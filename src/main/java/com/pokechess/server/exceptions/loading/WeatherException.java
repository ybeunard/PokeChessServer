package com.pokechess.server.exceptions.loading;

import com.pokechess.server.exceptions.ApiException;
import com.pokechess.server.exceptions.ValidationException;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;

public class WeatherException extends ApiException {
    private final static String DEFAULT_WEATHER_DUPLICATION_MESSAGE = "Weather data corrupted, duplicate id or name";
    private final static String DEFAULT_WEATHER_VALIDATION_MESSAGE = "Weather data corrupted, weather %s : %s";

    public enum WeatherExceptionType { WEATHER_DUPLICATION, WEATHER_VALIDATION }

    private final WeatherExceptionType type;
    private String name;
    private String message;

    public static WeatherException of(@NotNull WeatherExceptionType type) {
        return new WeatherException(type);
    }

    public static WeatherException of(String name, @NotNull ValidationException e) {
        return new WeatherException(WeatherExceptionType.WEATHER_VALIDATION, name, e.getMessage());
    }

    public static WeatherException of(String name, @NotNull ActionException e) {
        return new WeatherException(WeatherExceptionType.WEATHER_VALIDATION, name, e.getMessage());
    }

    private WeatherException(WeatherExceptionType type) {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
        this.type = type;
    }

    private WeatherException(WeatherExceptionType type, String name, String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
        this.type = type;
        this.name = name;
        this.message = message;
    }

    public WeatherExceptionType getType() {
        return type;
    }

    public String getMessage() {
        return switch (type) {
            case WEATHER_DUPLICATION -> DEFAULT_WEATHER_DUPLICATION_MESSAGE;
            case WEATHER_VALIDATION -> String.format(DEFAULT_WEATHER_VALIDATION_MESSAGE, name, message);
        };
    }
}
