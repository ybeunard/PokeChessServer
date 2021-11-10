package com.pokechess.server.models.globals.game.actions;

import com.pokechess.server.models.enumerations.Type;
import com.pokechess.server.validators.AttackValidator;
import com.pokechess.server.validators.GenericValidator;
import com.pokechess.server.validators.PokemonValidator;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Attack {
    private static final Integer DEFAULT_PRECISION = 0;

    private Type type;
    private Integer power;
    private Integer precision;
    private List<Effect> effects;

    private Attack() { }

    public static AttackBuilder builder() {
        return new AttackBuilder();
    }

    public static class AttackBuilder {
        private Type type;
        private Integer power;
        private Integer precision;
        private List<Effect> effects;

        public AttackBuilder type(Type type) {
            this.type = type;
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

        public AttackBuilder effects(List<Effect> effects) {
            this.effects = effects;
            return this;
        }

        public Attack build() {
            Attack attack = new Attack();
            attack.setType(type);
            attack.setPower(power);
            attack.setPrecision(Objects.nonNull(precision) ? precision : DEFAULT_PRECISION);
            attack.setEffects(effects);
            AttackValidator.validate(attack);
            return attack;
        }
    }

    @NonNull
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        PokemonValidator.notNoType(type);
        this.type = type;
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
        GenericValidator.notNull(precision);
        this.precision = precision;
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
