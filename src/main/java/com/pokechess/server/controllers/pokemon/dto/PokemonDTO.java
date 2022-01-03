package com.pokechess.server.controllers.pokemon.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PokemonDTO {
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
    private AttackDTO offensiveAttack;
    private AttackDTO defensiveAttack;
    private List<String> weaknesses;
    private List<String> resistances;
    private List<EvolutionDTO> evolutions;

    public String getPokemonId() {
        return pokemonId;
    }

    public void setPokemonId(String pokemonId) {
        this.pokemonId = pokemonId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getLifePoint() {
        return lifePoint;
    }

    public void setLifePoint(Integer lifePoint) {
        this.lifePoint = lifePoint;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public AttackDTO getOffensiveAttack() {
        return offensiveAttack;
    }

    public void setOffensiveAttack(AttackDTO offensiveAttack) {
        this.offensiveAttack = offensiveAttack;
    }

    public AttackDTO getDefensiveAttack() {
        return defensiveAttack;
    }

    public void setDefensiveAttack(AttackDTO defensiveAttack) {
        this.defensiveAttack = defensiveAttack;
    }

    public List<String> getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(List<String> weaknesses) {
        this.weaknesses = weaknesses;
    }

    public List<String> getResistances() {
        return resistances;
    }

    public void setResistances(List<String> resistances) {
        this.resistances = resistances;
    }

    public List<EvolutionDTO> getEvolutions() {
        return evolutions;
    }

    public void setEvolutions(List<EvolutionDTO> evolutions) {
        this.evolutions = evolutions;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PokemonDTO && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "PokemonDTO [pokemonId=%s, name=%s, generation=%s, level=%s, lifePoint=%s, baseSpeed=%s, size=%s, weight=%s, type=%s, type2=%s, offensiveAttack=%s, defensiveAttack=%s, weaknesses=%s, resistances=%s, evolutions=%s]", this.pokemonId, this.name, this.generation, this.level, this.lifePoint, this.baseSpeed, this.size, this.weight, this.type, this.type2, this.offensiveAttack, this.defensiveAttack, this.weaknesses, this.resistances, this.evolutions);
    }
}
