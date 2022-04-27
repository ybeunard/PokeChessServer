package com.pokechess.server.datasources.database.board.game.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Entity(name = "pokemon_place_entity")
public class PokemonPlaceEntity {
    private Integer id;
    private Integer position;
    private PokemonInstanceEntity pokemonInstance;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "pokemon_instance_id", referencedColumnName = "id")
    public PokemonInstanceEntity getPokemonInstance() {
        return pokemonInstance;
    }

    public void setPokemonInstance(PokemonInstanceEntity pokemonInstance) {
        this.pokemonInstance = pokemonInstance;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PokemonPlaceEntity && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "PokemonPlaceEntity [id=%s, position=%s, pokemonInstance=%s]", this.id, this.position, this.pokemonInstance);
    }
}
