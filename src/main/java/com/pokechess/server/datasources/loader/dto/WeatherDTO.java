package com.pokechess.server.datasources.loader.dto;

import com.pokechess.server.datasources.loader.dto.actions.EffectLoaderDTO;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public class WeatherDTO {
    private String id;
    private String name;
    private List<String> bonus;
    private List<String> malus;
    private List<EffectLoaderDTO> effects;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getBonus() {
        return bonus;
    }

    public List<String> getMalus() {
        return malus;
    }

    public List<EffectLoaderDTO> getEffects() {
        return effects;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof WeatherDTO && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "WeatherDTO [id=%s, name=%s, bonus=%s, malus=%s, effects=%s]", this.id, this.name, this.bonus, this.malus, this.effects);
    }
}
