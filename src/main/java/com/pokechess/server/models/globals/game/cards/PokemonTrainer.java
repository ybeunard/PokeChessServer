package com.pokechess.server.models.globals.game.cards;

import com.pokechess.server.models.globals.game.actions.Effect;
import com.pokechess.server.validators.GenericValidator;
import org.springframework.lang.NonNull;

import java.util.List;

public class PokemonTrainer {
    private Integer id;
    private List<Effect> effects;

    private PokemonTrainer() { }

    public static PokemonTrainerBuilder builder() {
        return new PokemonTrainerBuilder();
    }

    public static class PokemonTrainerBuilder {
        private Integer id;
        private List<Effect> effects;

        public PokemonTrainerBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public PokemonTrainerBuilder effects(List<Effect> effects) {
            this.effects = effects;
            return this;
        }

        public PokemonTrainer build() {
            PokemonTrainer pokemonTrainer = new PokemonTrainer();
            pokemonTrainer.setId(id);
            pokemonTrainer.setEffects(effects);
            return pokemonTrainer;
        }
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        GenericValidator.notNull(id, "id");
        this.id = id;
    }

    @NonNull
    public List<Effect> getEffects() {
        return effects;
    }

    public void setEffects(List<Effect> effects) {
        GenericValidator.notEmpty(effects, "effects");
        this.effects = effects;
    }
}
