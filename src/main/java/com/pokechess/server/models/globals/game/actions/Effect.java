package com.pokechess.server.models.globals.game.actions;

import com.pokechess.server.models.enumerations.PokemonStatus;
import com.pokechess.server.models.enumerations.Type;
import com.pokechess.server.models.enumerations.actions.DurationTime;
import com.pokechess.server.models.enumerations.actions.EffectType;
import com.pokechess.server.models.enumerations.actions.Target;
import com.pokechess.server.validators.EffectValidator;
import com.pokechess.server.validators.GenericValidator;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Effect {
    private EffectType type;
    private Trigger trigger;
    private List<Condition> conditions;
    private List<Target> targets;
    private Integer level;
    private List<Integer> levels;
    private PokemonStatus status;
    private Type typePokemon;
    private DurationTime duration;

    private Effect() { }

    public static EffectBuilder builder() {
        return new EffectBuilder();
    }

    public static class EffectBuilder {
        private EffectType type;
        private Trigger trigger;
        private List<Condition> conditions;
        private List<Target> targets;
        private Integer level;
        private List<Integer> levels;
        private PokemonStatus status;
        private Type typePokemon;
        private DurationTime duration;

        public EffectBuilder type(EffectType type) {
            this.type = type;
            return this;
        }

        public EffectBuilder trigger(Trigger trigger) {
            this.trigger = trigger;
            return this;
        }

        public EffectBuilder conditions(List<Condition> conditions) {
            this.conditions = conditions;
            return this;
        }

        public EffectBuilder targets(List<Target> targets) {
            this.targets = targets;
            return this;
        }

        public EffectBuilder level(Integer level) {
            this.level = level;
            return this;
        }

        public EffectBuilder levels(List<Integer> levels) {
            this.levels = levels;
            return this;
        }

        public EffectBuilder status(PokemonStatus status) {
            this.status = status;
            return this;
        }

        public EffectBuilder typePokemon(Type typePokemon) {
            this.typePokemon = typePokemon;
            return this;
        }

        public EffectBuilder duration(DurationTime duration) {
            this.duration = duration;
            return this;
        }

        public Effect build() {
            Effect effect = new Effect();
            effect.setType(type);
            effect.setTrigger(trigger);
            effect.setConditions(conditions);
            effect.setTargets(targets);
            effect.setLevel(level);
            effect.setLevels(levels);
            effect.setStatus(status);
            effect.setTypePokemon(typePokemon);
            effect.setDuration(duration);
            EffectValidator.validate(effect);
            return effect;
        }
    }

    @NonNull
    public EffectType getType() {
        return type;
    }

    public void setType(EffectType type) {
        GenericValidator.notNull(type, "type");
        this.type = type;
    }

    @Nullable
    public Trigger getTrigger() {
        return trigger;
    }

    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
    }

    @NonNull
    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        if (Objects.isNull(conditions)) {
            conditions = new ArrayList<>();
        }
        this.conditions = conditions;
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
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @NonNull
    public List<Integer> getLevels() {
        return levels;
    }

    public void setLevels(List<Integer> levels) {
        if (Objects.isNull(levels)) {
            levels = new ArrayList<>();
        }
        this.levels = levels;
    }

    @Nullable
    public PokemonStatus getStatus() {
        return status;
    }

    public void setStatus(PokemonStatus status) {
        this.status = status;
    }

    @Nullable
    public Type getTypePokemon() {
        return typePokemon;
    }

    public void setTypePokemon(Type typePokemon) {
        this.typePokemon = typePokemon;
    }

    @NonNull
    public DurationTime getDuration() {
        return duration;
    }

    public void setDuration(DurationTime duration) {
        GenericValidator.notNull(duration, "duration");
        this.duration = duration;
    }
}
