package com.pokechess.server.validators;

import com.pokechess.server.datasources.loader.dto.WeatherDTO;
import com.pokechess.server.exceptions.loading.WeatherException;

import java.util.List;
import java.util.Objects;

public class GameValidator {
    public static void validateWeatherDuplication(List<WeatherDTO> weatherDTOList) {
        if (weatherDTOList.stream().map(weather -> new DuplicateWeatherTest(weather.getId(), weather.getName())).distinct().count() != weatherDTOList.size()) {
            throw WeatherException.of(WeatherException.WeatherExceptionType.WEATHER_DUPLICATION);
        }
    }

    private record DuplicateWeatherTest(String id, String name) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            DuplicateWeatherTest that = (DuplicateWeatherTest) o;
            if (Objects.equals(id, that.id)) return true;
            return Objects.equals(name, that.name);
        }
    }
}
