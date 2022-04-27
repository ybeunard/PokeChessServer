package com.pokechess.server.datasources.database.party;

import com.pokechess.server.datasources.database.party.entity.PokemonDrawEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface PokemonDrawDatasource extends JpaRepository<PokemonDrawEntity, Integer> {
    void deleteByPartyId(Integer partyId);
    @Query(value = "DELETE FROM pokemon_draw_entity WHERE id=(SELECT pde.id FROM pokemon_draw_entity pde WHERE level=(SELECT MIN(dp.pokemon_level) FROM" +
            " (SELECT t.pokemon_level, SUM(tt.percentage) AS cum_weight FROM draw_percentages t" +
            " INNER JOIN draw_percentages tt ON tt.pokemon_level <= t.pokemon_level AND tt.player_level=:playerLevel AND t.player_level=:playerLevel" +
            " AND EXISTS(SELECT 1 FROM pokemon_draw_entity ttt WHERE party_id=:partyId AND level=t.pokemon_level)" +
            " AND  EXISTS(SELECT 1 FROM pokemon_draw_entity ttt WHERE party_id=:partyId AND level=tt.pokemon_level)" +
            " GROUP BY t.pokemon_level) dp WHERE" +
            " dp.cum_weight > (SELECT random() * SUM(percentage) FROM draw_percentages tw" +
            " WHERE tw.player_level=:playerLevel AND EXISTS(SELECT 1 FROM pokemon_draw_entity WHERE party_id=:partyId AND level=tw.pokemon_level)))" +
            " ORDER BY random() LIMIT 1) RETURNING *",
    nativeQuery = true)
    Optional<PokemonDrawEntity> drawPokemon(Integer partyId, Integer playerLevel);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM draw_percentages", nativeQuery = true)
    void deletePokemonDrawPercentages();
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO draw_percentages VALUES (:playerLevel, :pokemonLevel, :percentage)", nativeQuery = true)
    void savePokemonDrawPercentages(Integer playerLevel, Integer pokemonLevel, Double percentage);
}
