package com.pokechess.server.models.party.instances;

import com.pokechess.server.models.globals.game.cards.Pokemon;
import com.pokechess.server.validators.GenericValidator;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class PokemonInstance {
    private Integer id;
    private Pokemon pokemon;

    private PokemonInstance() { }

    public static PokemonInstanceBuilder builder() {
        return new PokemonInstanceBuilder();
    }

    public static class PokemonInstanceBuilder {
        private Integer id;
        private Pokemon pokemon;

        public PokemonInstanceBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public PokemonInstanceBuilder pokemon(Pokemon pokemon) {
            this.pokemon = pokemon;
            return this;
        }

        public PokemonInstance build() {
            PokemonInstance pokemonInstance = new PokemonInstance();
            pokemonInstance.setId(id);
            pokemonInstance.setPokemon(pokemon);
            return pokemonInstance;
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
    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        GenericValidator.notNull(pokemon, "pokemon");
        this.pokemon = pokemon;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PokemonInstance && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "PokemonInstance [id=%s, pokemon=%s]", this.id, this.pokemon);
    }
}
