package com.pokechess.server.models.globals.game.actions;

import com.pokechess.server.models.enumerations.PokemonStatus;
import com.pokechess.server.models.enumerations.actions.DurationTime;
import com.pokechess.server.models.enumerations.actions.Target;
import com.pokechess.server.models.enumerations.actions.TriggerAction;
import com.pokechess.server.validators.GenericValidator;
import com.pokechess.server.validators.TriggerValidator;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Trigger {
    private TriggerAction action;
    private List<Target> targets;
    private PokemonStatus status;
    private DurationTime duration;

    private Trigger() { }

    public static TriggerBuilder builder() {
        return new TriggerBuilder();
    }

    public static class TriggerBuilder {
        private TriggerAction action;
        private List<Target> targets;
        private PokemonStatus status;
        private DurationTime duration;

        public TriggerBuilder action(TriggerAction action) {
            this.action = action;
            return this;
        }

        public TriggerBuilder targets(List<Target> targets) {
            this.targets = targets;
            return this;
        }

        public TriggerBuilder status(PokemonStatus status) {
            this.status = status;
            return this;
        }

        public TriggerBuilder duration(DurationTime duration) {
            this.duration = duration;
            return this;
        }

        public Trigger build() {
            Trigger trigger = new Trigger();
            trigger.setAction(action);
            trigger.setTargets(targets);
            trigger.setStatus(status);
            trigger.setDuration(duration);
            TriggerValidator.validate(trigger);
            return trigger;
        }
    }

    @NonNull
    public TriggerAction getAction() {
        return action;
    }

    public void setAction(TriggerAction action) {
        GenericValidator.notNull(action);
        this.action = action;
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
    public PokemonStatus getStatus() {
        return status;
    }

    public void setStatus(PokemonStatus status) {
        this.status = status;
    }

    @NonNull
    public DurationTime getDuration() {
        return duration;
    }

    public void setDuration(DurationTime duration) {
        GenericValidator.notNull(duration);
        this.duration = duration;
    }
}
