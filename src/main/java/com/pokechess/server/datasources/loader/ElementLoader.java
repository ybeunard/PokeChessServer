package com.pokechess.server.datasources.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokechess.server.datasources.loader.dto.*;
import com.pokechess.server.exceptions.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ElementLoader {
    private static final String ERROR_LOADING_MESSAGE = "Error loading data from %s: Data corrupted";

    private final ObjectMapper mapper;
    private final Resource pokemonResources;
    private final Resource berryResources;
    private final Resource trainerObjectResources;
    private final Resource trainerResources;
    private final Resource weatherResources;

    public ElementLoader(ObjectMapper mapper, @Value("classpath:data/pokemon.json") Resource pokemonResources,
                         @Value("classpath:data/berry.json") Resource berryResources,
                         @Value("classpath:data/trainer_object.json") Resource trainerObjectResources,
                         @Value("classpath:data/trainer.json") Resource trainerResources,
                         @Value("classpath:data/weather.json") Resource weatherResources) {
        this.mapper = mapper;
        this.pokemonResources = pokemonResources;
        this.berryResources = berryResources;
        this.trainerObjectResources = trainerObjectResources;
        this.trainerResources = trainerResources;
        this.weatherResources = weatherResources;
    }

    @NonNull
    public List<PokemonLoaderDTO> loadPokemon() {
        return load(pokemonResources, PokemonLoaderDTO.class);
    }

    @NonNull
    public List<BerryDTO> loadBerries() {
        return load(berryResources, BerryDTO.class);
    }

    @NonNull
    public List<TrainerObjectDTO> loadTrainerObjects() {
        return load(trainerObjectResources, TrainerObjectDTO.class);
    }

    @NonNull
    public List<PokemonTrainerDTO> loadPokemonTrainers() {
        return load(trainerResources, PokemonTrainerDTO.class);
    }

    @NonNull
    public List<WeatherDTO> loadWeathers() {
        return load(weatherResources, WeatherDTO.class);
    }

    private <T> List<T> load(Resource resource, Class<T> classType) {
        try {
            return mapper.readValue(resource.getFile(), mapper.getTypeFactory().constructCollectionLikeType(List.class, classType));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(ERROR_LOADING_MESSAGE, resource.getFilename()));
        }
    }
}
