package com.pokechess.server.models.globals.game.cards;

import com.pokechess.server.exceptions.ValidationException;
import com.pokechess.server.exceptions.loading.PokemonException;
import com.pokechess.server.models.enumerations.Generation;
import com.pokechess.server.models.enumerations.Type;
import com.pokechess.server.models.globals.game.actions.Attack;
import com.pokechess.server.models.globals.game.actions.Evolution;
import com.pokechess.server.validators.GenericValidator;
import com.pokechess.server.validators.pokemon.PokemonValidator;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pokemon {
    private static final Generation DEFAULT_GENERATION = Generation.NO_GENERATION;
    private static final Type DEFAULT_TYPE_2 = Type.NO_TYPE;

    public static final Integer MIN_POKEMON_LEVEL = 0;
    public static final Integer MIN_POKEMON_LIFE_POINT = 1;
    public static final Integer MIN_POKEMON_BASE_SPEED = 1;

    private String pokemonId;
    private String name;
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
    private List<Evolution> evolutions;

    private Pokemon() { }

    public static PokemonBuilder builder() {
        return new PokemonBuilder();
    }

    public static class PokemonBuilder {
        private String pokemonId;
        private String name;
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
        private List<Evolution> evolutions;

        public PokemonBuilder pokemonId(String pokemonId) {
            this.pokemonId = pokemonId;
            return this;
        }

        public PokemonBuilder name(String name) {
            this.name = name;
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

        public PokemonBuilder evolutions(List<Evolution> evolutions) {
            this.evolutions = evolutions;
            return this;
        }

        public Pokemon build() {
            try {
                Pokemon pokemon = new Pokemon();
                pokemon.setPokemonId(pokemonId);
                pokemon.setName(name);
                pokemon.setGeneration(Objects.nonNull(generation) ? generation : DEFAULT_GENERATION);
                pokemon.setLevel(level);
                pokemon.setLifePoint(lifePoint);
                pokemon.setBaseSpeed(baseSpeed);
                pokemon.setSize(size);
                pokemon.setWeight(weight);
                pokemon.setType(type);
                pokemon.setType2(Objects.nonNull(type2) ? type2 : DEFAULT_TYPE_2);
                pokemon.setOffensiveAttack(offensiveAttack);
                pokemon.setDefensiveAttack(defensiveAttack);
                pokemon.setEvolutions(evolutions);
                PokemonValidator.validate(pokemon);
                return pokemon;
            } catch (ValidationException e) {
                throw PokemonException.of(pokemonId, e);
            }
        }
    }

    @NonNull
    public String getPokemonId() {
        return pokemonId;
    }

    public void setPokemonId(String pokemonId) {
        GenericValidator.notNull(pokemonId, "pokemonId");
        GenericValidator.pattern(pokemonId, "^((A|G|M|GM|F)-)?[0-9]{4}$", "pokemonId");
        this.pokemonId = pokemonId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        GenericValidator.notEmpty(name, "name");
        GenericValidator.max(name, 50, "name");
        this.name = name;
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
        GenericValidator.min(level, MIN_POKEMON_LEVEL, "level");
        this.level = level;
    }

    @NonNull
    public Integer getLifePoint() {
        return lifePoint;
    }

    public void setLifePoint(Integer lifePoint) {
        GenericValidator.notNull(lifePoint, "lifePoint");
        GenericValidator.min(lifePoint, MIN_POKEMON_LIFE_POINT, "lifePoint");
        this.lifePoint = lifePoint;
    }

    @NonNull
    public Integer getBaseSpeed() {
        return baseSpeed;
    }

    public void setBaseSpeed(Integer baseSpeed) {
        GenericValidator.notNull(baseSpeed, "baseSpeed");
        GenericValidator.min(baseSpeed, MIN_POKEMON_BASE_SPEED, "baseSpeed");
        this.baseSpeed = baseSpeed;
    }

    @Nullable
    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    @Nullable
    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    @NonNull
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        PokemonValidator.notNoType(type, "type");
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
    public List<Evolution> getEvolutions() {
        if (Objects.isNull(evolutions)) {
            evolutions = new ArrayList<>();
        }
        return evolutions;
    }

    public void setEvolutions(List<Evolution> evolutions) {
        this.evolutions = evolutions;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Pokemon && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "Pokemon [pokemonId=%s, name=%s, generation=%s, level=%s, lifePoint=%s, baseSpeed=%s, size=%s, weight=%s, type=%s, type2=%s, offensiveAttack=%s, defensiveAttack=%s, evolutions=%s]", this.pokemonId, this.name, this.generation, this.level, this.lifePoint, this.baseSpeed, this.size, this.weight, this.type, this.type2, this.offensiveAttack, this.defensiveAttack, this.evolutions);
    }
}
