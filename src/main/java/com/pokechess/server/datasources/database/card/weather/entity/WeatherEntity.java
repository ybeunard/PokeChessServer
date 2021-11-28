package com.pokechess.server.datasources.database.card.weather.entity;

import com.pokechess.server.datasources.database.card.entity.EffectEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.List;

@Entity(name = "weather_entity")
public class WeatherEntity {
    private String id;
    private String name;
    private String bonus;
    private String malus;
    private List<EffectEntity> effects;

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(unique = true, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    @Column(nullable = false)
    public String getMalus() {
        return malus;
    }

    public void setMalus(String malus) {
        this.malus = malus;
    }

    @OneToMany
    @JoinColumn(name = "weather_id", referencedColumnName = "id")
    public List<EffectEntity> getEffects() {
        return effects;
    }

    public void setEffects(List<EffectEntity> effects) {
        this.effects = effects;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof WeatherEntity && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "WeatherEntity [id=%s, name=%s, bonus=%s, malus=%s, effects=%s]", this.id, this.name, this.bonus, this.malus, this.effects);
    }
}
