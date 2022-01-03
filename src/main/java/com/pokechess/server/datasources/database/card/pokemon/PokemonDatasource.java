package com.pokechess.server.datasources.database.card.pokemon;

import com.pokechess.server.datasources.database.card.pokemon.entity.PokemonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PokemonDatasource extends JpaRepository<PokemonEntity, Integer> {
    @Query(value = "SELECT p FROM pokemon_entity p WHERE LOWER(p.name) LIKE %?1%")
    Page<PokemonEntity> findAllByName(String name, Pageable pageable);
}
