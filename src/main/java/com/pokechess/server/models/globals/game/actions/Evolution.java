package com.pokechess.server.models.globals.game.actions;

import com.pokechess.server.exceptions.ValidationException;
import com.pokechess.server.exceptions.loading.ActionException;
import com.pokechess.server.models.globals.game.cards.Pokemon;
import com.pokechess.server.models.globals.game.conditions.Condition;
import com.pokechess.server.validators.GenericValidator;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.lang.NonNull;

import java.util.List;

public class Evolution {
    private Pokemon pokemon;
    private String description;
    private List<Condition> conditions;

    private Evolution() { }

    public static EvolutionBuilder builder() {
        return new EvolutionBuilder();
    }

    public static class EvolutionBuilder {
        private Pokemon pokemon;
        private String description;
        private List<Condition> conditions;

        public EvolutionBuilder pokemon(Pokemon pokemon) {
            this.pokemon = pokemon;
            return this;
        }

        public EvolutionBuilder description(String description) {
            this.description = description;
            return this;
        }

        public EvolutionBuilder conditions(List<Condition> conditions) {
            this.conditions = conditions;
            return this;
        }

        public Evolution build() {
            try {
                Evolution evolution = new Evolution();
                evolution.setPokemon(pokemon);
                evolution.setDescription(description);
                evolution.setConditions(conditions);
                return evolution;
            } catch (ValidationException e) {
                throw ActionException.of(ActionException.ActionExceptionType.EVOLUTION_VALIDATION, null, e);
            }
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
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        GenericValidator.notEmpty(description, "description");
        GenericValidator.max(description, 255, "description");
        this.description = description;
    }

    @NonNull
    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        GenericValidator.notEmpty(conditions, "conditions");
        this.conditions = conditions;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Evolution && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "Evolution [pokemon=%s, description=%s, conditions=%s]", this.pokemon, this.description, this.conditions);
    }
}
