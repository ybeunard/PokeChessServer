package com.pokechess.server.datasources.loader.dto;

import com.pokechess.server.datasources.loader.dto.actions.AttackLoaderDTO;
import com.pokechess.server.datasources.loader.dto.actions.EvolutionLoaderDTO;

import java.util.List;

public class PokemonLoaderDTO {
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
    private AttackLoaderDTO offensiveAttack;
    private AttackLoaderDTO defensiveAttack;
    private List<EvolutionLoaderDTO> evolutions;

    public String getPokemonId() {
        return pokemonId;
    }

    public String getName() {
        return name;
    }

    public String getGeneration() {
        return generation;
    }

    public Integer getLevel() {
        return level;
    }

    public Integer getLifePoint() {
        return lifePoint;
    }

    public Integer getBaseSpeed() {
        return baseSpeed;
    }

    public Float getSize() {
        return size;
    }

    public Float getWeight() {
        return weight;
    }

    public String getType() {
        return type;
    }

    public String getType2() {
        return type2;
    }

    public AttackLoaderDTO getOffensiveAttack() {
        return offensiveAttack;
    }

    public AttackLoaderDTO getDefensiveAttack() {
        return defensiveAttack;
    }

    public List<EvolutionLoaderDTO> getEvolutions() {
        return evolutions;
    }
}
