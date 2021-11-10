package com.pokechess.server.models.globals.game.actions;

import com.pokechess.server.models.enumerations.Synergy;
import com.pokechess.server.models.enumerations.Type;
import com.pokechess.server.models.enumerations.actions.ConditionType;
import com.pokechess.server.models.enumerations.actions.Target;
import com.pokechess.server.validators.ConditionValidator;
import com.pokechess.server.validators.GenericValidator;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class Condition {
    private ConditionType type;
    private Target target;
    private Integer level;
    private Synergy synergy;
    private Type typePokemon;
    private Integer successRate;

    private Condition() { }

    public static ConditionBuilder builder() {
        return new ConditionBuilder();
    }

    public static class ConditionBuilder {
        private ConditionType type;
        private Target target;
        private Integer level;
        private Synergy synergy;
        private Type typePokemon;
        private Integer successRate;

        public ConditionBuilder type(ConditionType type) {
            this.type = type;
            return this;
        }

        public ConditionBuilder target(Target target) {
            this.target = target;
            return this;
        }

        public ConditionBuilder level(Integer level) {
            this.level = level;
            return this;
        }

        public ConditionBuilder synergy(Synergy synergy) {
            this.synergy = synergy;
            return this;
        }

        public ConditionBuilder typePokemon(Type typePokemon) {
            this.typePokemon = typePokemon;
            return this;
        }

        public ConditionBuilder successRate(Integer successRate) {
            this.successRate = successRate;
            return this;
        }

        public Condition build() {
            Condition condition = new Condition();
            condition.setType(type);
            condition.setTarget(target);
            condition.setLevel(level);
            condition.setSynergy(synergy);
            condition.setTypePokemon(typePokemon);
            condition.setSuccessRate(successRate);
            ConditionValidator.validate(condition);
            return condition;
        }
    }

    @NonNull
    public ConditionType getType() {
        return type;
    }

    public void setType(ConditionType type) {
        GenericValidator.notNull(type);
        this.type = type;
    }

    @Nullable
    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    @Nullable
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Nullable
    public Synergy getSynergy() {
        return synergy;
    }

    public void setSynergy(Synergy synergy) {
        this.synergy = synergy;
    }

    @Nullable
    public Type getTypePokemon() {
        return typePokemon;
    }

    public void setTypePokemon(Type typePokemon) {
        this.typePokemon = typePokemon;
    }

    @Nullable
    public Integer getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(Integer successRate) {
        this.successRate = successRate;
    }
}
