package com.pokechess.server.models.globals.game.cards;

import com.pokechess.server.models.globals.game.effects.Effect;
import com.pokechess.server.validators.PokemonObjectValidator;

import java.util.List;

public class Berry extends PokemonObject {
    private Berry() { }

    public static BerryBuilder builder() {
        return new BerryBuilder();
    }

    public static class BerryBuilder {
        private Integer id;
        private List<Effect> combatEffects;
        private List<Effect> outOfCombatEffects;

        public BerryBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public BerryBuilder combatEffects(List<Effect> combatEffects) {
            this.combatEffects = combatEffects;
            return this;
        }

        public BerryBuilder outOfCombatEffects(List<Effect> outOfCombatEffects) {
            this.outOfCombatEffects = outOfCombatEffects;
            return this;
        }

        public Berry build() {
            Berry berry = new Berry();
            berry.setId(id);
            berry.setCombatEffects(combatEffects);
            berry.setOutOfCombatEffects(outOfCombatEffects);
            PokemonObjectValidator.validate(berry);
            return berry;
        }
    }
}
