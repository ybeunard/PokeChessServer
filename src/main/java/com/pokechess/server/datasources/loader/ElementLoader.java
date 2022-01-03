package com.pokechess.server.datasources.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokechess.server.datasources.loader.dto.*;
import com.pokechess.server.exceptions.ApiException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.util.List;

@Component
public class ElementLoader {
    private static final String ERROR_LOADING_MESSAGE = "Error loading data from %s: Data corrupted";
    private static final String POKEMON_RESOURCES = "data/pokemon.json";
    private static final String BERRY_RESOURCES = "data/berry.json";
    private static final String TRAINER_OBJECT_RESOURCES = "data/trainer_object.json";
    private static final String TRAINER_RESOURCES = "data/trainer.json";
    private static final String WEATHER_RESOURCES = "data/weather.json";

    private final ObjectMapper mapper;

    public ElementLoader(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @NonNull
    public List<PokemonLoaderDTO> loadPokemon() {
        return load(POKEMON_RESOURCES, PokemonLoaderDTO.class);
    }

    @NonNull
    public List<BerryDTO> loadBerries() {
        return load(BERRY_RESOURCES, BerryDTO.class);
    }

    @NonNull
    public List<TrainerObjectDTO> loadTrainerObjects() {
        return load(TRAINER_OBJECT_RESOURCES, TrainerObjectDTO.class);
    }

    @NonNull
    public List<PokemonTrainerDTO> loadPokemonTrainers() {
        return load(TRAINER_RESOURCES, PokemonTrainerDTO.class);
    }

    @NonNull
    public List<WeatherDTO> loadWeathers() {
        return load(WEATHER_RESOURCES, WeatherDTO.class);
    }

    private <T> List<T> load(String resource, Class<T> classType) {
        try {
            return mapper.readValue(new InputStreamReader(new ClassPathResource(resource).getInputStream()), mapper.getTypeFactory().constructCollectionLikeType(List.class, classType));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(ERROR_LOADING_MESSAGE, resource));
        }
    }
}
