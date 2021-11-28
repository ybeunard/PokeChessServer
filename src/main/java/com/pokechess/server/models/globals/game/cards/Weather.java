package com.pokechess.server.models.globals.game.cards;

import com.pokechess.server.exceptions.ValidationException;
import com.pokechess.server.exceptions.loading.WeatherException;
import com.pokechess.server.models.globals.game.effects.Effect;
import com.pokechess.server.validators.GenericValidator;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Weather {
    private String id;
    private String name;
    private List<String> bonus;
    private List<String> malus;
    private List<Effect> effects;

    private Weather() { }

    public static WeatherBuilder builder() {
        return new WeatherBuilder();
    }

    public static class WeatherBuilder {
        private String id;
        private String name;
        private List<String> bonus;
        private List<String> malus;
        private List<Effect> effects;

        public WeatherBuilder id(String id) {
            this.id = id;
            return this;
        }

        public WeatherBuilder name(String name) {
            this.name = name;
            return this;
        }

        public WeatherBuilder bonus(List<String> bonus) {
            this.bonus = bonus;
            return this;
        }

        public WeatherBuilder malus(List<String> malus) {
            this.malus = malus;
            return this;
        }

        public WeatherBuilder effects(List<Effect> effects) {
            this.effects = effects;
            return this;
        }

        public Weather build() {
            try {
                Weather weather = new Weather();
                weather.setId(id);
                weather.setName(name);
                weather.setBonus(bonus);
                weather.setMalus(malus);
                weather.setEffects(effects);
                return weather;
            } catch (ValidationException e) {
                throw WeatherException.of(name, e);
            }
        }
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(String id) {
        GenericValidator.notNull(id, "id");
        GenericValidator.pattern(id, "^[0-9]{4}$", "id");
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        GenericValidator.notEmpty(name, "name");
        GenericValidator.max(name, 50, "name");
        this.name = name;
    }

    @NonNull
    public List<String> getBonus() {
        return bonus;
    }

    public void setBonus(List<String> bonus) {
        GenericValidator.notEmpty(bonus, "bonus");
        this.bonus = bonus;
    }

    @NonNull
    public List<String> getMalus() {
        return malus;
    }

    public void setMalus(List<String> malus) {
        GenericValidator.notEmpty(malus, "malus");
        this.malus = malus;
    }

    @NonNull
    public List<Effect> getEffects() {
        return effects;
    }

    public void setEffects(List<Effect> effects) {
        if (Objects.isNull(effects)) {
            effects = new ArrayList<>();
        }
        this.effects = effects;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Weather && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "Weather [id=%s, bonus=%s, malus=%s, effects=%s]", this.id, this.bonus, this.malus, this.effects);
    }
}
