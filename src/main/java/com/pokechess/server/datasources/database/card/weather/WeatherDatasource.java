package com.pokechess.server.datasources.database.card.weather;

import com.pokechess.server.datasources.database.card.weather.entity.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherDatasource extends JpaRepository<WeatherEntity, Integer> {
}
