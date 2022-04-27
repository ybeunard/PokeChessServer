package com.pokechess.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

public class GameException extends ApiException {
    private static final String DEFAULT_POKEMON_HAND_NOT_FOUND_MESSAGE = "The pokemon haven't be found in the trainer hand.";
    private static final String DEFAULT_POKEMON_BOARD_NOT_FOUND_MESSAGE = "The pokemon haven't be found in the trainer board.";
    private static final String DEFAULT_CANNOT_MOVE_ON_BENCH_OVERLOAD_MESSAGE = "Cannot move a pokemon on board overload.";
    private static final String DEFAULT_POKEMON_BENCH_PLACE_NOT_FOUND_MESSAGE = "No place found in the player bench.";
    private static final String DEFAULT_NOT_ENOUGH_MONEY_MESSAGE = "Player has not enough money.";

    public enum GameExceptionType { POKEMON_HAND_NOT_FOUND, POKEMON_BOARD_NOT_FOUND, CANNOT_MOVE_ON_BENCH_OVERLOAD,
        POKEMON_BENCH_PLACE_NOT_FOUND, NOT_ENOUGH_MONEY }

    private final GameException.GameExceptionType type;

    public static GameException of(@NonNull GameException.GameExceptionType type) {
        return switch (type) {
            case POKEMON_HAND_NOT_FOUND, POKEMON_BOARD_NOT_FOUND, CANNOT_MOVE_ON_BENCH_OVERLOAD,
                    POKEMON_BENCH_PLACE_NOT_FOUND, NOT_ENOUGH_MONEY -> new GameException(HttpStatus.BAD_REQUEST, type);
        };
    }

    private GameException(HttpStatus status, GameException.GameExceptionType type) {
        super(status);
        this.type = type;
    }

    public GameException.GameExceptionType getType() {
        return type;
    }

    public String getMessage() {
        return switch (type) {
            case POKEMON_HAND_NOT_FOUND -> DEFAULT_POKEMON_HAND_NOT_FOUND_MESSAGE;
            case POKEMON_BOARD_NOT_FOUND -> DEFAULT_POKEMON_BOARD_NOT_FOUND_MESSAGE;
            case CANNOT_MOVE_ON_BENCH_OVERLOAD -> DEFAULT_CANNOT_MOVE_ON_BENCH_OVERLOAD_MESSAGE;
            case POKEMON_BENCH_PLACE_NOT_FOUND -> DEFAULT_POKEMON_BENCH_PLACE_NOT_FOUND_MESSAGE;
            case NOT_ENOUGH_MONEY -> DEFAULT_NOT_ENOUGH_MONEY_MESSAGE;
        };
    }
}
