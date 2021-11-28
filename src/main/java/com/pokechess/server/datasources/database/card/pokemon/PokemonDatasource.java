package com.pokechess.server.datasources.database.card.pokemon;

import com.pokechess.server.datasources.database.card.pokemon.entity.PokemonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonDatasource extends JpaRepository<PokemonEntity, Integer> {
}
