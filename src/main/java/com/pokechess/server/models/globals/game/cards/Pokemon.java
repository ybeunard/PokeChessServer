package com.pokechess.server.models.globals.game.cards;

import com.pokechess.server.models.enumerations.Type;
import com.pokechess.server.models.enumerations.Generation;
import com.pokechess.server.models.globals.game.actions.Attack;
import com.pokechess.server.models.globals.game.actions.Effect;
import com.pokechess.server.models.globals.game.actions.Evolution;
import com.pokechess.server.validators.GenericValidator;
import com.pokechess.server.validators.PokemonValidator;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pokemon {
    private static final Type DEFAULT_TYPE_2 = Type.NO_TYPE;

    private Integer pokemonId;
    private Generation generation;
    private Integer level;
    private Integer lifePoint;
    private Integer baseSpeed;
    private Float size;
    private Float weight;
    private Type type;
    private Type type2;
    private Attack offensiveAttack;
    private Attack defensiveAttack;
    private List<Type> weaknesses;
    private List<Type> resistances;
    private List<Evolution> evolutions;

    private Pokemon() { }

    public static PokemonBuilder builder() {
        return new PokemonBuilder();
    }

    public static class PokemonBuilder {
        private Integer pokemonId;
        private Generation generation;
        private Integer level;
        private Integer lifePoint;
        private Integer baseSpeed;
        private Float size;
        private Float weight;
        private Type type;
        private Type type2;
        private Attack offensiveAttack;
        private Attack defensiveAttack;
        private List<Type> weaknesses;
        private List<Type> resistances;
        private List<Evolution> evolutions;

        public PokemonBuilder pokemonId(Integer pokemonId) {
            this.pokemonId = pokemonId;
            return this;
        }

        public PokemonBuilder generation(Generation generation) {
            this.generation = generation;
            return this;
        }

        public PokemonBuilder level(Integer level) {
            this.level = level;
            return this;
        }

        public PokemonBuilder lifePoint(Integer lifePoint) {
            this.lifePoint = lifePoint;
            return this;
        }

        public PokemonBuilder baseSpeed(Integer baseSpeed) {
            this.baseSpeed = baseSpeed;
            return this;
        }

        public PokemonBuilder size(Float size) {
            this.size = size;
            return this;
        }

        public PokemonBuilder weight(Float weight) {
            this.weight = weight;
            return this;
        }

        public PokemonBuilder type(Type type) {
            this.type = type;
            return this;
        }

        public PokemonBuilder type2(Type type2) {
            this.type2 = type2;
            return this;
        }

        public PokemonBuilder offensiveAttack(Attack offensiveAttack) {
            this.offensiveAttack = offensiveAttack;
            return this;
        }

        public PokemonBuilder defensiveAttack(Attack defensiveAttack) {
            this.defensiveAttack = defensiveAttack;
            return this;
        }

        public PokemonBuilder weaknesses(List<Type> weaknesses) {
            this.weaknesses = weaknesses;
            return this;
        }

        public PokemonBuilder resistances(List<Type> resistances) {
            this.resistances = resistances;
            return this;
        }

        public PokemonBuilder evolutions(List<Evolution> evolutions) {
            this.evolutions = evolutions;
            return this;
        }

        public Pokemon build() {
            Pokemon pokemon = new Pokemon();
            pokemon.setPokemonId(pokemonId);
            pokemon.setGeneration(generation);
            pokemon.setLevel(level);
            pokemon.setLifePoint(lifePoint);
            pokemon.setBaseSpeed(baseSpeed);
            pokemon.setSize(size);
            pokemon.setWeight(weight);
            pokemon.setType(type);
            pokemon.setType2(Objects.nonNull(type2) ? type2 : DEFAULT_TYPE_2);
            pokemon.setOffensiveAttack(offensiveAttack);
            pokemon.setDefensiveAttack(defensiveAttack);
            pokemon.setWeaknesses(weaknesses);
            pokemon.setResistances(resistances);
            pokemon.setEvolutions(evolutions);
            return pokemon;
        }
    }

    @NonNull
    public Integer getPokemonId() {
        return pokemonId;
    }

    public void setPokemonId(Integer pokemonId) {
        GenericValidator.notNull(pokemonId, "pokemonId");
        this.pokemonId = pokemonId;
    }

    @NonNull
    public Generation getGeneration() {
        return generation;
    }

    public void setGeneration(Generation generation) {
        GenericValidator.notNull(generation, "generation");
        this.generation = generation;
    }

    @NonNull
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        GenericValidator.notNull(level, "level");
        this.level = level;
    }

    @NonNull
    public Integer getLifePoint() {
        return lifePoint;
    }

    public void setLifePoint(Integer lifePoint) {
        GenericValidator.notNull(lifePoint, "lifePoint");
        this.lifePoint = lifePoint;
    }

    @NonNull
    public Integer getBaseSpeed() {
        return baseSpeed;
    }

    public void setBaseSpeed(Integer baseSpeed) {
        GenericValidator.notNull(baseSpeed, "baseSpeed");
        this.baseSpeed = baseSpeed;
    }

    @NonNull
    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        GenericValidator.notNull(size, "size");
        this.size = size;
    }

    @NonNull
    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        GenericValidator.notNull(weight, "weight");
        this.weight = weight;
    }

    @NonNull
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        PokemonValidator.notNoType(type);
        this.type = type;
    }

    @NonNull
    public Type getType2() {
        return type2;
    }

    public void setType2(Type type2) {
        GenericValidator.notNull(type2, "type2");
        this.type2 = type2;
    }

    @NonNull
    public Attack getOffensiveAttack() {
        return offensiveAttack;
    }

    public void setOffensiveAttack(Attack offensiveAttack) {
        GenericValidator.notNull(offensiveAttack, "offensiveAttack");
        this.offensiveAttack = offensiveAttack;
    }

    @NonNull
    public Attack getDefensiveAttack() {
        return defensiveAttack;
    }

    public void setDefensiveAttack(Attack defensiveAttack) {
        GenericValidator.notNull(defensiveAttack, "defensiveAttack");
        this.defensiveAttack = defensiveAttack;
    }

    @NonNull
    public List<Type> getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(List<Type> weaknesses) {
        if (Objects.isNull(weaknesses)) {
            weaknesses = new ArrayList<>();
        }
        PokemonValidator.notContainsNoType(weaknesses);
        this.weaknesses = weaknesses;
    }

    @NonNull
    public List<Type> getResistances() {
        return resistances;
    }

    public void setResistances(List<Type> resistances) {
        if (Objects.isNull(resistances)) {
            resistances = new ArrayList<>();
        }
        PokemonValidator.notContainsNoType(resistances);
        this.resistances = resistances;
    }

    @NonNull
    public List<Evolution> getEvolutions() {
        if (Objects.isNull(evolutions)) {
            evolutions = new ArrayList<>();
        }
        return evolutions;
    }

    public void setEvolutions(List<Evolution> evolutions) {
        this.evolutions = evolutions;
    }
}
