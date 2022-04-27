package com.pokechess.server.datasources.database.party.entity;

import com.pokechess.server.datasources.database.card.pokemon.entity.PokemonEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Entity(name = "pokemon_draw_entity")
public class PokemonDrawEntity {
    private Integer id;
    private PartyEntity party;
    private Integer level;
    private PokemonEntity pokemon;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "party_id", referencedColumnName = "id")
    public PartyEntity getParty() {
        return party;
    }

    public void setParty(PartyEntity party) {
        this.party = party;
    }

    @Column(nullable = false)
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "pokemon_id", referencedColumnName = "pokemon_id")
    public PokemonEntity getPokemon() {
        return pokemon;
    }

    public void setPokemon(PokemonEntity pokemon) {
        this.pokemon = pokemon;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PokemonDrawEntity && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "PokemonDrawEntity [id=%s, party=%s, level=%s, pokemon=%s]", this.id, this.party, this.level, this.pokemon);
    }
}
/*
SELECT * FROM pokemon_draw_entity pde INNER JOIN pokemon_entity pe ON pde.pokemon_id=pe.pokemon_id AND level=(SELECT MIN(dp.pokemon_level) FROM draw_percentages dp
	INNER JOIN (SELECT t.pokemon_level, SUM(tt.percentage) AS cum_weight FROM draw_percentages t
		INNER JOIN draw_percentages tt ON tt.pokemon_level <= t.pokemon_level AND tt.player_level=3 AND t.player_level=3
			AND EXISTS(SELECT 1 FROM pokemon_draw_entity ttt INNER JOIN pokemon_entity tttt ON ttt.pokemon_id=tttt.pokemon_id AND tttt.level=t.pokemon_level)
			AND  EXISTS(SELECT 1 FROM pokemon_draw_entity ttt INNER JOIN pokemon_entity tttt ON ttt.pokemon_id=tttt.pokemon_id AND tttt.level=tt.pokemon_level)
			GROUP BY t.pokemon_level) tc ON tc.pokemon_level = dp.pokemon_level AND dp.player_level=3
		AND tc.cum_weight > (SELECT random() * SUM(percentage) FROM draw_percentages tw WHERE tw.player_level=3 AND
		EXISTS(SELECT 1 FROM  pokemon_draw_entity ttt INNER JOIN pokemon_entity tttt ON ttt.pokemon_id=tttt.pokemon_id AND tttt.level=tw.pokemon_level)))
	ORDER BY random() LIMIT 1;
 */
/*
SELECT p.* FROM public.pokemon_entity p WHERE NOT EXISTS(SELECT 1 FROM public.evolution_entity e WHERE p.pokemon_id = e.pokemon_evolved_id);
 */