package com.pokechess.server.repositories.pokemon;

import com.pokechess.server.datasources.database.card.pokemon.PokemonDatasource;
import com.pokechess.server.datasources.database.card.pokemon.mapper.PokemonEntityMapper;
import com.pokechess.server.models.globals.game.cards.Pokemon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class PokemonRepository {
    private final PokemonDatasource pokemonDatasource;

    public PokemonRepository(PokemonDatasource pokemonDatasource) {
        this.pokemonDatasource = pokemonDatasource;
    }

    public Page<Pokemon> getPaginatedPokemon(int page, int size, String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            return this.pokemonDatasource.findAll(PageRequest.of(page - 1, size))
                    .map(PokemonEntityMapper::mapPokemonFromPokemonEntity);
        }
        return this.pokemonDatasource.findAllByName(name.toLowerCase(), PageRequest.of(page - 1, size))
                .map(PokemonEntityMapper::mapPokemonFromPokemonEntity);
    }
}
