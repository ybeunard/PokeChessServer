package com.pokechess.server.models.party;

import com.pokechess.server.models.enumerations.Synergy;
import com.pokechess.server.validators.GenericValidator;
import com.pokechess.server.validators.PokemonValidator;
import org.springframework.lang.NonNull;

import java.util.*;

public class BoardGame {
    public static final Integer POKEMON_PLACE_LIST_LENGTH = 5;
    public static final Integer DEFAULT_POKEMON_CENTER_COUNTER = 0;

    private List<PokemonPlace> offensiveLine;
    private List<PokemonPlace> defensiveLine;
    private List<PokemonPlace> bench;
    private List<PokemonPlace> benchOverload;
    private List<Synergy> synergies;
    private List<Object> inventory;
    private PokemonPlace pokemonCenter;
    private Integer pokemonCenterCounter;

    private BoardGame() { }

    public static BoardGameBuilder builder() {
        return new BoardGameBuilder();
    }

    public static class BoardGameBuilder {
        private List<PokemonPlace> offensiveLine;
        private List<PokemonPlace> defensiveLine;
        private List<PokemonPlace> bench;
        private List<PokemonPlace> benchOverload;
        private List<Synergy> synergies;
        private List<Object> inventory;
        private PokemonPlace pokemonCenter;
        private Integer pokemonCenterCounter;

        public BoardGameBuilder offensiveLine(List<PokemonPlace> offensiveLine) {
            this.offensiveLine = offensiveLine;
            return this;
        }

        public BoardGameBuilder defensiveLine(List<PokemonPlace> defensiveLine) {
            this.defensiveLine = defensiveLine;
            return this;
        }

        public BoardGameBuilder bench(List<PokemonPlace> bench) {
            this.bench = bench;
            return this;
        }

        public BoardGameBuilder benchOverload(List<PokemonPlace> benchOverload) {
            this.offensiveLine = benchOverload;
            return this;
        }

        public BoardGameBuilder synergies(List<Synergy> synergies) {
            this.synergies = synergies;
            return this;
        }

        public BoardGameBuilder inventory(List<Object> inventory) {
            this.inventory = inventory;
            return this;
        }

        public BoardGameBuilder pokemonCenter(PokemonPlace pokemonCenter) {
            this.pokemonCenter = pokemonCenter;
            return this;
        }

        public BoardGameBuilder pokemonCenterCounter(Integer pokemonCenterCounter) {
            this.pokemonCenterCounter = pokemonCenterCounter;
            return this;
        }

        public BoardGame build() {
            BoardGame boardGame = new BoardGame();
            boardGame.setOffensiveLine(offensiveLine);
            boardGame.setDefensiveLine(defensiveLine);
            boardGame.setBench(bench);
            boardGame.setBenchOverload(benchOverload);
            boardGame.setSynergies(synergies);
            boardGame.setInventory(inventory);
            boardGame.setPokemonCenter(pokemonCenter);
            boardGame.setPokemonCenterCounter(Objects.nonNull(pokemonCenterCounter) ? pokemonCenterCounter : DEFAULT_POKEMON_CENTER_COUNTER);
            return boardGame;
        }
    }

    @NonNull
    public List<PokemonPlace> getOffensiveLine() {
        return offensiveLine;
    }

    public void setOffensiveLine(List<PokemonPlace> offensiveLine) {
        PokemonValidator.correctPokemonPlaceListSize(offensiveLine);
        this.offensiveLine = offensiveLine;
    }

    @NonNull
    public List<PokemonPlace> getDefensiveLine() {
        return defensiveLine;
    }

    public void setDefensiveLine(List<PokemonPlace> defensiveLine) {
        PokemonValidator.correctPokemonPlaceListSize(defensiveLine);
        this.defensiveLine = defensiveLine;
    }

    @NonNull
    public List<PokemonPlace> getBench() {
        return bench;
    }

    public void setBench(List<PokemonPlace> bench) {
        PokemonValidator.correctPokemonPlaceListSize(bench);
        this.bench = bench;
    }

    @NonNull
    public List<PokemonPlace> getBenchOverload() {
        return benchOverload;
    }

    public void setBenchOverload(List<PokemonPlace> benchOverload) {
        if (Objects.isNull(benchOverload)) {
            benchOverload = new ArrayList<>();
        }
        this.benchOverload = benchOverload;
    }

    @NonNull
    public List<Synergy> getSynergies() {
        return synergies;
    }

    public void setSynergies(List<Synergy> synergies) {
        if (Objects.isNull(synergies)) {
            synergies = new ArrayList<>();
        }
        this.synergies = synergies;
    }

    @NonNull
    public List<Object> getInventory() {
        return inventory;
    }

    public void setInventory(List<Object> inventory) {
        if (Objects.isNull(inventory)) {
            inventory = new ArrayList<>();
        }
        this.inventory = inventory;
    }

    @NonNull
    public PokemonPlace getPokemonCenter() {
        return pokemonCenter;
    }

    public void setPokemonCenter(PokemonPlace pokemonCenter) {
        GenericValidator.notNull(pokemonCenter, "pokemonCenter");
        this.pokemonCenter = pokemonCenter;
    }

    @NonNull
    public Integer getPokemonCenterCounter() {
        return pokemonCenterCounter;
    }

    public void setPokemonCenterCounter(Integer pokemonCenterCounter) {
        GenericValidator.notNull(pokemonCenterCounter, "pokemonCenterCounter");
        this.pokemonCenterCounter = pokemonCenterCounter;
    }
}
