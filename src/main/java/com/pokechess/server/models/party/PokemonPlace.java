package com.pokechess.server.models.party;

import com.pokechess.server.models.party.instances.PokemonInstance;
import org.springframework.lang.Nullable;

public class PokemonPlace {
    private PokemonInstance pokemon;

    private PokemonPlace() { }

    public static PokemonPlaceBuilder builder() {
        return new PokemonPlaceBuilder();
    }

    public static class PokemonPlaceBuilder {
        private PokemonInstance pokemon;

        public PokemonPlaceBuilder pokemon(PokemonInstance pokemon) {
            this.pokemon = pokemon;
            return this;
        }

        public PokemonPlace build() {
            PokemonPlace pokemonPlace = new PokemonPlace();
            pokemonPlace.setPokemon(pokemon);
            return pokemonPlace;
        }
    }

    @Nullable
    public PokemonInstance getPokemon() {
        return pokemon;
    }

    public void setPokemon(PokemonInstance pokemon) {
        this.pokemon = pokemon;
    }
}
