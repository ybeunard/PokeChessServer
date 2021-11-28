package com.pokechess.server.repositories.loader;

import com.pokechess.server.datasources.database.card.pokemon.PokemonDatasource;
import com.pokechess.server.datasources.database.card.pokemon.mapper.PokemonEntityMapper;
import com.pokechess.server.datasources.loader.ElementLoader;
import com.pokechess.server.datasources.loader.dto.PokemonLoaderDTO;
import com.pokechess.server.datasources.loader.dto.WeatherDTO;
import com.pokechess.server.datasources.loader.mapper.ObjectMapper;
import com.pokechess.server.datasources.loader.mapper.PokemonLoaderDTOMapper;
import com.pokechess.server.models.globals.game.cards.*;
import com.pokechess.server.validators.PokemonValidator;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LoaderRepository {
    private final ElementLoader elementLoader;
    private final PokemonDatasource pokemonDatasource;

    public LoaderRepository(ElementLoader elementLoader,
                            PokemonDatasource pokemonDatasource) {
        this.elementLoader = elementLoader;
        this.pokemonDatasource = pokemonDatasource;
    }

    public void loadAllPokemon() {
        List<PokemonLoaderDTO> pokemonLoaderDTOList = elementLoader.loadPokemon();
        PokemonValidator.validatePokemonDuplication(pokemonLoaderDTOList);
        List<Pokemon> pokemonList = PokemonLoaderDTOMapper.mapPokemonListFromPokemonDTOList(pokemonLoaderDTOList);
        this.pokemonDatasource.deleteAll();
        this.pokemonDatasource.saveAll(PokemonEntityMapper
                .mapPokemonListToPokemonEntityList(pokemonList));
    }

    public List<TrainerObject> loadTrainerObjectDraw() {
        return Optional.ofNullable(elementLoader.loadTrainerObjects())
                .map(ObjectMapper::mapTrainerObjectListFromTrainerObjectDTOList)
                .orElseThrow(); // TODO THROW
    }

    public List<Berry> loadBerries() {
        return Optional.ofNullable(elementLoader.loadBerries())
                .map(ObjectMapper::mapBerryListFromBerryDTOList)
                .orElseThrow(); // TODO THROW
    }

    public List<PokemonTrainer> loadPokemonTrainers() {
        return Optional.ofNullable(elementLoader.loadPokemonTrainers())
                .map(ObjectMapper::mapPokemonTrainerListFromPokemonTrainerDTOList)
                .orElseThrow(); // TODO THROW
    }

    public void loadAllWeather() {
        List<WeatherDTO> weatherDTOList = elementLoader.loadWeathers();
        // GameValidator.validateWeatherDuplication(weatherDTOList);
        // List<Weather> weatherList = ObjectMapper.mapWeatherListFromWeatherDTOList(weatherDTOList);
        // this.weatherDatasource.deleteAll();
        // this.weatherDatasource.saveAll(WeatherEntityMapper.mapWeatherListToWeatherEntityList(weatherList));
    }
}
