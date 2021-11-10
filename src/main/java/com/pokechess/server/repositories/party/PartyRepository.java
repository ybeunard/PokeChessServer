package com.pokechess.server.repositories.party;

import com.pokechess.server.datasources.loader.ElementLoader;
import com.pokechess.server.datasources.loader.mapper.ObjectMapper;
import com.pokechess.server.datasources.loader.mapper.PokemonMapper;
import com.pokechess.server.models.globals.game.cards.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PartyRepository {
    private final ElementLoader elementLoader;

    public PartyRepository(ElementLoader elementLoader) {
        this.elementLoader = elementLoader;
    }

    public Map<Integer, List<Pokemon>> loadPokemonDraw() {
        return Optional.ofNullable(elementLoader.loadPokemon())
                .map(pokemonLoaded -> pokemonLoaded.stream().map(PokemonMapper::mapPokemonFromPokemonDTO)
                        .collect(Collectors.groupingBy(Pokemon::getLevel)))
                .orElseThrow(); // TODO THROW
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

    public List<Weather> loadWeathers() {
        return Optional.ofNullable(elementLoader.loadWeathers())
                .map(ObjectMapper::mapWeatherListFromWeatherDTOList)
                .orElseThrow(); // TODO THROW
    }
}
