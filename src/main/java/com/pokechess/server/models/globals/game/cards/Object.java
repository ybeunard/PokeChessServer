package com.pokechess.server.models.globals.game.cards;

import com.pokechess.server.models.globals.game.actions.Effect;
import com.pokechess.server.validators.GenericValidator;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Object {
    private Integer id;
    private List<Effect> combatEffects;
    private List<Effect> outOfCombatEffects;

    protected Object() { }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        GenericValidator.notNull(id);
        this.id = id;
    }

    @NonNull
    public List<Effect> getCombatEffects() {
        return combatEffects;
    }

    public void setCombatEffects(List<Effect> combatEffects) {
        if (Objects.isNull(combatEffects)) {
            combatEffects = new ArrayList<>();
        }
        this.combatEffects = combatEffects;
    }

    @NonNull
    public List<Effect> getOutOfCombatEffects() {
        return outOfCombatEffects;
    }

    public void setOutOfCombatEffects(List<Effect> outOfCombatEffects) {
        if (Objects.isNull(outOfCombatEffects)) {
            outOfCombatEffects = new ArrayList<>();
        }
        this.outOfCombatEffects = outOfCombatEffects;
    }
}
