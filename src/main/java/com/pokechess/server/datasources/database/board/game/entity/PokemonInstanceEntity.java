package com.pokechess.server.datasources.database.board.game.entity;

import com.pokechess.server.datasources.database.card.pokemon.entity.PokemonEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Entity(name = "pokemon_instance_entity")
public class PokemonInstanceEntity {
    private Integer id;
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
    @JoinColumn(name = "pokemon_id", referencedColumnName = "pokemon_id")
    public PokemonEntity getPokemon() {
        return pokemon;
    }

    public void setPokemon(PokemonEntity pokemon) {
        this.pokemon = pokemon;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PokemonInstanceEntity && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "PokemonInstanceEntity [id=%s, pokemon=%s]", this.id, this.pokemon);
    }
}
