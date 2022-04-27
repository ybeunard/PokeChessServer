package com.pokechess.server.datasources.database.board.game.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.List;

@Entity(name = "board_game_entity")
public class BoardGameEntity {
    private Integer id;
    private List<PokemonPlaceEntity> offensiveLine;
    private List<PokemonPlaceEntity> defensiveLine;
    private List<PokemonPlaceEntity> bench;
    private List<PokemonPlaceEntity> benchOverload;
    private PokemonPlaceEntity pokemonCenter;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinColumn(name="board_game_offensive_line_id", referencedColumnName="id")
    public List<PokemonPlaceEntity> getOffensiveLine() {
        return offensiveLine;
    }

    public void setOffensiveLine(List<PokemonPlaceEntity> offensiveLine) {
        this.offensiveLine = offensiveLine;
    }

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinColumn(name="board_game_defensive_line_id", referencedColumnName="id")
    public List<PokemonPlaceEntity> getDefensiveLine() {
        return defensiveLine;
    }

    public void setDefensiveLine(List<PokemonPlaceEntity> defensiveLine) {
        this.defensiveLine = defensiveLine;
    }

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinColumn(name="board_game_bench_id", referencedColumnName="id")
    public List<PokemonPlaceEntity> getBench() {
        return bench;
    }

    public void setBench(List<PokemonPlaceEntity> bench) {
        this.bench = bench;
    }

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="board_game_bench_overload_id", referencedColumnName="id")
    public List<PokemonPlaceEntity> getBenchOverload() {
        return benchOverload;
    }

    public void setBenchOverload(List<PokemonPlaceEntity> benchOverload) {
        this.benchOverload = benchOverload;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "pokemon_center", referencedColumnName = "id", unique = true, nullable = false)
    public PokemonPlaceEntity getPokemonCenter() {
        return pokemonCenter;
    }

    public void setPokemonCenter(PokemonPlaceEntity pokemonCenter) {
        this.pokemonCenter = pokemonCenter;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof BoardGameEntity && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "BoardGameEntity [id=%s, offensiveLine=%s, defensiveLine=%s, bench=%s, benchOverload=%s, pokemonCenter=%s]", this.id, this.offensiveLine, this.defensiveLine, this.bench, this.benchOverload, this.pokemonCenter);
    }
}
