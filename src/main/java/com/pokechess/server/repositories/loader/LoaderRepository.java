package com.pokechess.server.repositories.loader;

import com.fasterxml.jackson.databind.JsonNode;
import com.pokechess.server.datasources.database.card.pokemon.PokemonDatasource;
import com.pokechess.server.datasources.database.card.pokemon.mapper.PokemonEntityMapper;
import com.pokechess.server.datasources.database.party.PokemonDrawDatasource;
import com.pokechess.server.datasources.loader.ElementLoader;
import com.pokechess.server.datasources.loader.dto.PokemonLoaderDTO;
import com.pokechess.server.datasources.loader.dto.WeatherDTO;
import com.pokechess.server.datasources.loader.mapper.ObjectMapper;
import com.pokechess.server.datasources.loader.mapper.PokemonLoaderDTOMapper;
import com.pokechess.server.exceptions.ApiException;
import com.pokechess.server.models.globals.game.cards.Berry;
import com.pokechess.server.models.globals.game.cards.Pokemon;
import com.pokechess.server.models.globals.game.cards.PokemonTrainer;
import com.pokechess.server.models.globals.game.cards.TrainerObject;
import com.pokechess.server.validators.pokemon.PokemonValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class LoaderRepository {
    private static final String PLAYER_LEVEL_NOT_DISTINCT = "Player level are not distinct";
    private static final String POKEMON_LEVEL_NOT_DISTINCT = "Pokemon level for player %s are not distinct";
    private static final String PERCENTAGE_SUM_NOT_ONE = "Pokemon draw percentage sums for player level %s not equals to 1";

    private final ElementLoader elementLoader;
    private final PokemonDatasource pokemonDatasource;
    private final PokemonDrawDatasource pokemonDrawDatasource;

    public LoaderRepository(ElementLoader elementLoader,
                            PokemonDatasource pokemonDatasource,
                            PokemonDrawDatasource pokemonDrawDatasource) {
        this.elementLoader = elementLoader;
        this.pokemonDatasource = pokemonDatasource;
        this.pokemonDrawDatasource = pokemonDrawDatasource;
    }

    public void loadAllPokemon() {
        List<PokemonLoaderDTO> pokemonLoaderDTOList = elementLoader.loadPokemon();
        PokemonValidator.validatePokemonDuplication(pokemonLoaderDTOList);
        List<Pokemon> pokemonList = PokemonLoaderDTOMapper.mapPokemonListFromPokemonDTOList(pokemonLoaderDTOList);
        this.pokemonDatasource.deleteAll();
        this.pokemonDatasource.saveAll(PokemonEntityMapper
                .mapPokemonListToPokemonEntityList(pokemonList));
    }

    public void loadAllPokemonDrawPercentage() {
        this.pokemonDrawDatasource.deletePokemonDrawPercentages();
        JsonNode pokemonDrawPercentageJsonObject = elementLoader.loadPokemonDrawPercentage();
        List<JsonNode> elements = new ArrayList<>();
        pokemonDrawPercentageJsonObject.elements().forEachRemaining(elements::add);
        if (elements.size() != elements.stream().map(element -> element.path("playerLevel").asInt()).distinct().count()) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, PLAYER_LEVEL_NOT_DISTINCT);
        }
        elements.forEach(element -> {
            int playerLevel = element.path("playerLevel").asInt();
            List<JsonNode> percentages = new ArrayList<>();
            element.path("percentages").elements().forEachRemaining(percentages::add);
            if (percentages.size() != percentages.stream().map(percentage -> percentage.path("pokemonLevel").asInt()).distinct().count()) {
                throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(POKEMON_LEVEL_NOT_DISTINCT, playerLevel));
            }
            if (percentages.stream().map(percentage -> percentage.path("percentage").asDouble())
                    .reduce(0.0, (i, j) -> new BigDecimal(i + j).setScale(3, RoundingMode.HALF_UP).doubleValue()) != 1) {
                throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(PERCENTAGE_SUM_NOT_ONE, playerLevel));
            }
            percentages.forEach(percentage -> {
                int pokemonLevel = percentage.path("pokemonLevel").asInt();
                double percent = percentage.path("percentage").asDouble();
                this.pokemonDrawDatasource.savePokemonDrawPercentages(playerLevel, pokemonLevel, percent);
            });
        });
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
