package com.pokechess.server.models.globals.game.cards;

import com.pokechess.server.models.globals.game.actions.Effect;
import com.pokechess.server.validators.GenericValidator;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Weather {
    private Integer id;
    private List<Effect> effects;

    private Weather() { }

    public static WeatherBuilder builder() {
        return new WeatherBuilder();
    }

    public static class WeatherBuilder {
        private Integer id;
        private List<Effect> effects;

        public WeatherBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public WeatherBuilder effects(List<Effect> effects) {
            this.effects = effects;
            return this;
        }

        public Weather build() {
            Weather weather = new Weather();
            weather.setId(id);
            weather.setEffects(effects);
            return weather;
        }
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        GenericValidator.notNull(id);
        this.id = id;
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
}
