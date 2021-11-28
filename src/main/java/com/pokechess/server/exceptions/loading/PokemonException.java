package com.pokechess.server.exceptions.loading;

import com.pokechess.server.exceptions.ApiException;
import com.pokechess.server.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

public class PokemonException extends ApiException {
    private final static String DEFAULT_POKEMON_DUPLICATION_MESSAGE = "Pokemon data corrupted, duplicate pokemon id or name detected";
    private final static String DEFAULT_POKEMON_DUPLICATE_TYPE_MESSAGE = "Pokemon data corrupted, pokemon %s : cannot have two similar type";
    private final static String DEFAULT_POKEMON_DEFENSIVE_ATTACK_POWER_MESSAGE = "Pokemon data corrupted, pokemon %s : defensive attack cannot have power";
    private final static String DEFAULT_POKEMON_VALIDATION_MESSAGE = "Pokemon data corrupted, pokemon %s : %s";

    public enum PokemonExceptionType { POKEMON_DUPLICATION, POKEMON_DUPLICATE_TYPE, POKEMON_DEFENSIVE_ATTACK_POWER, POKEMON_VALIDATION }

    private final PokemonExceptionType type;
    private String pokemonId;
    private String message;

    public static PokemonException of(@NonNull PokemonExceptionType type) {
        return new PokemonException(type);
    }

    public static PokemonException of(@NonNull PokemonExceptionType type, String pokemonId) {
        return switch (type) {
            case POKEMON_DUPLICATE_TYPE, POKEMON_DEFENSIVE_ATTACK_POWER -> new PokemonException(type, pokemonId);
            default -> new PokemonException(type);
        };
    }

    public static PokemonException of(String pokemonId,@NonNull ValidationException e) {
        return new PokemonException(PokemonExceptionType.POKEMON_VALIDATION, pokemonId, e.getMessage());
    }

    public static PokemonException of(String pokemonId,@NonNull ActionException e) {
        return new PokemonException(PokemonExceptionType.POKEMON_VALIDATION, pokemonId, e.getMessage());
    }

    public static PokemonException of(String pokemonId,@NonNull AttackException e) {
        return new PokemonException(PokemonExceptionType.POKEMON_VALIDATION, pokemonId, e.getMessage());
    }

    private PokemonException(PokemonExceptionType type) {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
        this.type = type;
    }

    private PokemonException(PokemonExceptionType type, String pokemonId) {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
        this.type = type;
        this.pokemonId = pokemonId;
    }

    private PokemonException(PokemonExceptionType type, String pokemonId, String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
        this.type = type;
        this.pokemonId = pokemonId;
        this.message = message;
    }

    public PokemonExceptionType getType() {
        return this.type;
    }

    @Override
    public String getMessage() {
        return switch (this.type) {
            case POKEMON_DUPLICATION -> DEFAULT_POKEMON_DUPLICATION_MESSAGE;
            case POKEMON_DUPLICATE_TYPE -> String.format(DEFAULT_POKEMON_DUPLICATE_TYPE_MESSAGE, this.pokemonId);
            case POKEMON_DEFENSIVE_ATTACK_POWER -> String.format(DEFAULT_POKEMON_DEFENSIVE_ATTACK_POWER_MESSAGE, this.pokemonId);
            case POKEMON_VALIDATION -> String.format(DEFAULT_POKEMON_VALIDATION_MESSAGE, pokemonId, message);
        };
    }
}
