package com.pokechess.server.models.globals.game.cards;

import com.pokechess.server.models.globals.game.effects.Effect;

import java.util.List;

public class TrainerObject extends PokemonObject {
    private TrainerObject() { }

    public static TrainerObjectBuilder builder() {
        return new TrainerObjectBuilder();
    }

    public static class TrainerObjectBuilder {
        private Integer id;
        private List<Effect> combatEffects;
        private List<Effect> outOfCombatEffects;

        public TrainerObjectBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public TrainerObjectBuilder combatEffects(List<Effect> combatEffects) {
            this.combatEffects = combatEffects;
            return this;
        }

        public TrainerObjectBuilder outOfCombatEffects(List<Effect> outOfCombatEffects) {
            this.outOfCombatEffects = outOfCombatEffects;
            return this;
        }

        public TrainerObject build() {
            TrainerObject trainerObject = new TrainerObject();
            trainerObject.setId(id);
            trainerObject.setCombatEffects(combatEffects);
            trainerObject.setOutOfCombatEffects(outOfCombatEffects);
            return trainerObject;
        }
    }
}
