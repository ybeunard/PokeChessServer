package com.pokechess.server.models.party;

import com.pokechess.server.models.party.instances.PokemonInstance;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.lang.Nullable;

public class PokemonPlace {
    private Integer id;
    private PokemonInstance pokemon;

    private PokemonPlace() { }

    public static PokemonPlaceBuilder builder() {
        return new PokemonPlaceBuilder();
    }

    public static class PokemonPlaceBuilder {
        private Integer id;
        private PokemonInstance pokemon;

        public PokemonPlaceBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public PokemonPlaceBuilder pokemon(PokemonInstance pokemon) {
            this.pokemon = pokemon;
            return this;
        }

        public PokemonPlace build() {
            PokemonPlace pokemonPlace = new PokemonPlace();
            pokemonPlace.setId(id);
            pokemonPlace.setPokemon(pokemon);
            return pokemonPlace;
        }
    }

    @Nullable
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Nullable
    public PokemonInstance getPokemon() {
        return pokemon;
    }

    public void setPokemon(PokemonInstance pokemon) {
        this.pokemon = pokemon;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PokemonPlace && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "PokemonPlace [id=%s, pokemon=%s]", this.id, this.pokemon);
    }
}
