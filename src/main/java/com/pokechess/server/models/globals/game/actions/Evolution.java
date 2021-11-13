package com.pokechess.server.models.globals.game.actions;

import com.pokechess.server.models.globals.game.cards.Pokemon;
import com.pokechess.server.validators.GenericValidator;
import org.springframework.lang.NonNull;

import java.util.List;

public class Evolution {
    private Pokemon pokemon;
    private List<Condition> conditions;

    private Evolution() { }

    public static EvolutionBuilder builder() {
        return new EvolutionBuilder();
    }

    public static class EvolutionBuilder {
        private Pokemon pokemon;
        private List<Condition> conditions;

        public EvolutionBuilder pokemon(Pokemon pokemon) {
            this.pokemon = pokemon;
            return this;
        }

        public EvolutionBuilder conditions(List<Condition> conditions) {
            this.conditions = conditions;
            return this;
        }

        public Evolution build() {
            Evolution evolution = new Evolution();
            evolution.setPokemon(pokemon);
            evolution.setConditions(conditions);
            return evolution;
        }
    }

    @NonNull
    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        GenericValidator.notNull(pokemon, "pokemon");
        this.pokemon = pokemon;
    }

    @NonNull
    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        GenericValidator.notEmpty(conditions, "conditions");
        this.conditions = conditions;
    }
}
