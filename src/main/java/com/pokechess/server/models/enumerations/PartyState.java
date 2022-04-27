package com.pokechess.server.models.enumerations;

import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum PartyState {
    CREATION, STARTED, FIRST_CAROUSEL, FIRST_CAROUSEL_END, FIRST_TURN, FIRST_TURN_END, TURN_PREPARATION_PHASE, TURN_ACTION_PHASE, TURN_ACTION_PHASE_END, TURN_FIGHT_PHASE,
    TURN_FIGHT_PHASE_END, DELETED, TERMINATED;

    public static final List<PartyState> ADD_PLAYER_PARTY_STATE = List.of(PartyState.CREATION);
    public static final List<PartyState> DRAW_POKEMON_PARTY_STATE = List.of(PartyState.FIRST_CAROUSEL, PartyState.TURN_PREPARATION_PHASE,
            TURN_ACTION_PHASE);
    public static final List<PartyState> ROLL_POKEMON_PARTY_STATE = List.of(PartyState.TURN_ACTION_PHASE);
    public static final List<PartyState> BUY_EXPERIENCE_PARTY_STATE = List.of(PartyState.TURN_ACTION_PHASE);
    public static final List<PartyState> BUY_POKEMON_PARTY_STATE = List.of(PartyState.FIRST_CAROUSEL, PartyState.TURN_ACTION_PHASE);
    public static final List<PartyState> MOVE_POKEMON_PARTY_STATE = List.of(PartyState.FIRST_TURN, PartyState.TURN_ACTION_PHASE);
    public static final List<PartyState> SELL_POKEMON_PARTY_STATE = List.of(PartyState.TURN_ACTION_PHASE);
    public static final List<PartyState> INCREMENT_TURN_POKEMON_PARTY_STATE = List.of(PartyState.TURN_PREPARATION_PHASE);

    public static final Map<PartyState, List<PartyState>> PARTY_STATE_TRANSITION_MAP = new HashMap<>() {{
        put(STARTED, List.of(CREATION));
        put(FIRST_CAROUSEL, List.of(STARTED));
        put(FIRST_CAROUSEL_END, List.of(FIRST_CAROUSEL));
        put(FIRST_TURN, List.of(FIRST_CAROUSEL_END));
        put(FIRST_TURN_END, List.of(FIRST_TURN));
        put(TURN_PREPARATION_PHASE, List.of(TURN_FIGHT_PHASE_END));
        put(TURN_ACTION_PHASE, List.of(TURN_PREPARATION_PHASE));
        put(TURN_ACTION_PHASE_END, List.of(TURN_ACTION_PHASE));
        put(TURN_FIGHT_PHASE, List.of(FIRST_TURN_END, TURN_ACTION_PHASE_END));
        put(TURN_FIGHT_PHASE_END, List.of(TURN_FIGHT_PHASE));
        put(DELETED, List.of( CREATION));
        put(TERMINATED, List.of(STARTED, FIRST_CAROUSEL, FIRST_CAROUSEL_END, TURN_PREPARATION_PHASE,
                TURN_ACTION_PHASE, TURN_ACTION_PHASE_END, TURN_FIGHT_PHASE, TURN_FIGHT_PHASE_END));
    }};

    @Nullable
    public static PartyState getEnum(String name) {
        for (PartyState e : values()) {
            if (e.name().equals(name)) {
                return e;
            }
        }
        return null;
    }
}
