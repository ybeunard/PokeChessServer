package com.pokechess.server.datasources.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokechess.server.datasources.loader.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ElementLoader {
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

    public List<PokemonDTO> loadPokemon() {
        return load(pokemonResources, PokemonDTO.class);
    }

    public List<BerryDTO> loadBerries() {
        return load(berryResources, BerryDTO.class);
    }

    public List<TrainerObjectDTO> loadTrainerObjects() {
        return load(trainerObjectResources, TrainerObjectDTO.class);
    }

    public List<PokemonTrainerDTO> loadPokemonTrainers() {
        return load(trainerResources, PokemonTrainerDTO.class);
    }

    public List<WeatherDTO> loadWeathers() {
        return load(weatherResources, WeatherDTO.class);
    }

    private <T> List<T> load(Resource resource, Class<T> classType) {
        try {
            return mapper.readValue(resource.getFile(), mapper.getTypeFactory().constructCollectionLikeType(List.class, classType));
        } catch (Exception e) {
            // TODO THROW
            throw new RuntimeException();
        }
    }
}
