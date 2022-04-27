package com.pokechess.server.models.globals.game.actions;

import com.pokechess.server.exceptions.ValidationException;
import com.pokechess.server.exceptions.loading.AttackException;
import com.pokechess.server.models.enumerations.Type;
import com.pokechess.server.models.enumerations.actions.Target;
import com.pokechess.server.models.globals.game.effects.ApplyWhenEffect;
import com.pokechess.server.validators.GenericValidator;
import com.pokechess.server.validators.pokemon.PokemonValidator;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Attack {
    private static final Integer DEFAULT_PRECISION = 6;
    private static final Integer DEFAULT_PRIORITY = 0;

    private String name;
    private String description;
    private Type type;
    private List<Target> targets;
    private Integer power;
    private Integer precision;
    private Integer priority;
    private List<ApplyWhenEffect> effects;

    private Attack() { }

    public static AttackBuilder builder() {
        return new AttackBuilder();
    }

    public static class AttackBuilder {
        private String name;
        private String description;
        private Type type;
        private List<Target> targets;
        private Integer power;
        private Integer precision;
        private Integer priority;
        private List<ApplyWhenEffect> effects;

        public AttackBuilder name(String name) {
            this.name = name;
            return this;
        }

        public AttackBuilder description(String description) {
            this.description = description;
            return this;
        }

        public AttackBuilder type(Type type) {
            this.type = type;
            return this;
        }

        public AttackBuilder targets(List<Target> targets) {
            this.targets = targets;
            return this;
        }

        public AttackBuilder power(Integer power) {
            this.power = power;
            return this;
        }

        public AttackBuilder precision(Integer precision) {
            this.precision = precision;
            return this;
        }

        public AttackBuilder priority(Integer priority) {
            this.priority = priority;
            return this;
        }

        public AttackBuilder effects(List<ApplyWhenEffect> effects) {
            this.effects = effects;
            return this;
        }

        public Attack build() {
            try {
                Attack attack = new Attack();
                attack.setName(name);
                attack.setDescription(description);
                attack.setType(type);
                attack.setTargets(targets);
                attack.setPower(power);
                attack.setPrecision(Objects.nonNull(precision) ? precision : DEFAULT_PRECISION);
                attack.setPriority(Objects.nonNull(priority) ? priority : DEFAULT_PRIORITY);
                attack.setEffects(effects);
                PokemonValidator.validate(attack);
                return attack;
            } catch (ValidationException e) {
                throw AttackException.of(name, e);
            }
        }
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
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        GenericValidator.notEmpty(description, "description");
        GenericValidator.max(description, 255, "description");
        this.description = description;
    }

    @NonNull
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        PokemonValidator.notNoType(type, "type");
        this.type = type;
    }

    @NonNull
    public List<Target> getTargets() {
        return targets;
    }

    public void setTargets(List<Target> targets) {
        if (Objects.isNull(targets)) {
            targets = new ArrayList<>();
        }
        this.targets = targets;
    }

    @Nullable
    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    @NonNull
    public Integer getPrecision() {
        return precision;
    }

    public void setPrecision(Integer precision) {
        GenericValidator.notNull(precision, "precision");
        this.precision = precision;
    }

    @NonNull
    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        GenericValidator.notNull(priority, "priority");
        this.priority = priority;
    }

    @NonNull
    public List<ApplyWhenEffect> getEffects() {
        return effects;
    }

    public void setEffects(List<ApplyWhenEffect> effects) {
        if (Objects.isNull(effects)) {
            effects = new ArrayList<>();
        }
        this.effects = effects;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Attack && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "Attack [name=%s, description=%s, type=%s, targets=%s, power=%s, precision=%s, priority=%s, effects=%s]", this.name, this.description, this.type, this.targets, this.power, this.precision, this.priority, this.effects);
    }
}
