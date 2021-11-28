package com.pokechess.server.datasources.database.card.pokemon.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.List;

@Entity(name = "pokemon_entity")
public class PokemonEntity {
    private String pokemonId;
    private String name;
    private String generation;
    private Integer level;
    private Integer lifePoint;
    private Integer baseSpeed;
    private Float size;
    private Float weight;
    private String type;
    private String type2;
    private AttackEntity offensiveAttack;
    private AttackEntity defensiveAttack;
    private List<EvolutionEntity> evolutions;

    @Id
    @Column(name = "pokemon_id")
    public String getPokemonId() {
        return pokemonId;
    }

    public void setPokemonId(String pokemonId) {
        this.pokemonId = pokemonId;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    @Column(nullable = false)
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Column(name = "life_point", nullable = false)
    public Integer getLifePoint() {
        return lifePoint;
    }

    public void setLifePoint(Integer lifePoint) {
        this.lifePoint = lifePoint;
    }

    @Column(name = "base_speed", nullable = false)
    public Integer getBaseSpeed() {
        return baseSpeed;
    }

    public void setBaseSpeed(Integer baseSpeed) {
        this.baseSpeed = baseSpeed;
    }

    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    @Column(nullable = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "type_two", nullable = false)
    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    @OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinColumn(name="offensive_attack_id", referencedColumnName="id", nullable = false)
    public AttackEntity getOffensiveAttack() {
        return offensiveAttack;
    }

    public void setOffensiveAttack(AttackEntity offensiveAttack) {
        this.offensiveAttack = offensiveAttack;
    }

    @OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinColumn(name="defensive_attack_id", referencedColumnName="id", nullable = false)
    public AttackEntity getDefensiveAttack() {
        return defensiveAttack;
    }

    public void setDefensiveAttack(AttackEntity defensiveAttack) {
        this.defensiveAttack = defensiveAttack;
    }

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinColumn(name="pokemon_base_id", referencedColumnName="pokemon_id", nullable = false)
    public List<EvolutionEntity> getEvolutions() {
        return evolutions;
    }

    public void setEvolutions(List<EvolutionEntity> evolutions) {
        this.evolutions = evolutions;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PokemonEntity && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "PokemonEntity [pokemonId=%s, name=%s, generation=%s, level=%s, lifePoint=%s, baseSpeed=%s, size=%s, weight=%s, type=%s, type2=%s, offensiveAttack=%s, defensiveAttack=%s, evolutions=%s]", this.pokemonId, this.name, this.generation, this.level, this.lifePoint, this.baseSpeed, this.size, this.weight, this.type, this.type2, this.offensiveAttack, this.defensiveAttack, this.evolutions);
    }
}
