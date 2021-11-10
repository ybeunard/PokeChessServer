package com.pokechess.server.datasources.loader.dto;

import com.pokechess.server.datasources.loader.dto.actions.AttackDTO;
import com.pokechess.server.datasources.loader.dto.actions.EvolutionDTO;
import org.springframework.lang.Nullable;

import java.util.List;

public class PokemonDTO {
    private Integer pokemonId;
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

    @Nullable
    public Integer getPokemonId() {
        return pokemonId;
    }

    @Nullable
    public String getGeneration() {
        return generation;
    }

    @Nullable
    public Integer getLevel() {
        return level;
    }

    @Nullable
    public Integer getLifePoint() {
        return lifePoint;
    }

    @Nullable
    public Integer getBaseSpeed() {
        return baseSpeed;
    }

    @Nullable
    public Float getSize() {
        return size;
    }

    @Nullable
    public Float getWeight() {
        return weight;
    }

    @Nullable
    public String getType() {
        return type;
    }

    @Nullable
    public String getType2() {
        return type2;
    }

    @Nullable
    public AttackDTO getOffensiveAttack() {
        return offensiveAttack;
    }

    @Nullable
    public AttackDTO getDefensiveAttack() {
        return defensiveAttack;
    }

    @Nullable
    public List<String> getWeaknesses() {
        return weaknesses;
    }

    @Nullable
    public List<String> getResistances() {
        return resistances;
    }

    @Nullable
    public List<EvolutionDTO> getEvolutions() {
        return evolutions;
    }
}
