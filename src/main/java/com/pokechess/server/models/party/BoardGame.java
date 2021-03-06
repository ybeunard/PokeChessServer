package com.pokechess.server.models.party;

import com.pokechess.server.models.enumerations.Synergy;
import com.pokechess.server.validators.GenericValidator;
import com.pokechess.server.validators.pokemon.PokemonValidator;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.*;

public class BoardGame {
    public static final Integer POKEMON_STARTING_POSITION = 3;
    public static final Integer POKEMON_PLACE_LIST_LENGTH = 5;
    public static final Integer MIN_POKEMON_PLACE_BEFORE_LEVEL_LIMIT = 2;
    public static final Integer MAX_POKEMON_PLACE_BEFORE_LEVEL_LIMIT = 4;
    public static final Integer LEVEL_LIMIT = 5;

    private Integer id;
    private List<PokemonPlace> offensiveLine;
    private List<PokemonPlace> defensiveLine;
    private List<PokemonPlace> bench;
    private List<PokemonPlace> benchOverload;
    private List<Synergy> synergies;
    private List<Object> inventory;
    private PokemonPlace pokemonCenter;

    private BoardGame() { }

    public static BoardGameBuilder builder() {
        return new BoardGameBuilder();
    }

    public static class BoardGameBuilder {
        private Integer id;
        private List<PokemonPlace> offensiveLine;
        private List<PokemonPlace> defensiveLine;
        private List<PokemonPlace> bench;
        private List<PokemonPlace> benchOverload;
        private List<Synergy> synergies;
        private List<Object> inventory;
        private PokemonPlace pokemonCenter;

        public BoardGameBuilder id(Integer id) {
            this.id = id;
            return this;
        }

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
            this.benchOverload = benchOverload;
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

        public BoardGame build() {
            BoardGame boardGame = new BoardGame();
            boardGame.setId(id);
            boardGame.setOffensiveLine(offensiveLine);
            boardGame.setDefensiveLine(defensiveLine);
            boardGame.setBench(bench);
            boardGame.setBenchOverload(benchOverload);
            boardGame.setSynergies(synergies);
            boardGame.setInventory(inventory);
            boardGame.setPokemonCenter(pokemonCenter);
            return boardGame;
        }
    }

    @Nullable
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NonNull
    public List<PokemonPlace> getOffensiveLine() {
        return offensiveLine;
    }

    public void setOffensiveLine(List<PokemonPlace> offensiveLine) {
        PokemonValidator.correctPokemonPlaceListSize(offensiveLine, "offensiveLine");
        this.offensiveLine = offensiveLine;
    }

    @NonNull
    public List<PokemonPlace> getDefensiveLine() {
        return defensiveLine;
    }

    public void setDefensiveLine(List<PokemonPlace> defensiveLine) {
        PokemonValidator.correctPokemonPlaceListSize(defensiveLine, "defensiveLine");
        this.defensiveLine = defensiveLine;
    }

    @NonNull
    public List<PokemonPlace> getBench() {
        return bench;
    }

    public void setBench(List<PokemonPlace> bench) {
        PokemonValidator.correctPokemonPlaceListSize(bench, "bench");
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

    @Override
    public boolean equals(Object o) {
        return o instanceof BoardGame && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "BoardGame [id=%s, offensiveLine=%s, defensiveLine=%s, bench=%s, benchOverload=%s, synergies=%s, inventory=%s, pokemonCenter=%s]", this.id, this.offensiveLine, this.defensiveLine, this.bench, this.benchOverload, this.synergies, this.inventory, this.pokemonCenter);
    }
}
