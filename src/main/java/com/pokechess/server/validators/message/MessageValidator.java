package com.pokechess.server.validators.message;

import com.pokechess.server.controllers.game.dto.message.MoveMessageDTO;
import com.pokechess.server.controllers.game.dto.message.SellMessageDTO;
import com.pokechess.server.models.enumerations.BoardPlace;

import java.util.Objects;

import static com.pokechess.server.models.party.BoardGame.POKEMON_PLACE_LIST_LENGTH;

public class MessageValidator {
    public static boolean validateMoveMessageDTO(MoveMessageDTO message) {
        if (Objects.isNull(message) || Objects.isNull(message.getId())) return false;

        BoardPlace place = BoardPlace.getEnum(message.getPlace());
        BoardPlace destinationPlace = BoardPlace.getEnum(message.getDestinationPlace());

        if (Objects.isNull(place) || Objects.isNull(message.getPosition())
                || Objects.isNull(destinationPlace) || BoardPlace.BENCH_OVERLOAD.equals(destinationPlace)
                || Objects.isNull(message.getDestinationPosition()))
            return false;

        return message.getPosition() >= 1 && message.getPosition() <= POKEMON_PLACE_LIST_LENGTH
                && message.getDestinationPosition() >= 1 && message.getDestinationPosition() <= POKEMON_PLACE_LIST_LENGTH;
    }

    public static boolean validateSellMessageDTO(SellMessageDTO message) {
        if (Objects.isNull(message) || Objects.isNull(message.getId())) return false;

        BoardPlace place = BoardPlace.getEnum(message.getPlace());
        if (Objects.isNull(place) || Objects.isNull(message.getPosition())) return false;

        return message.getPosition() >= 1 && message.getPosition() <= POKEMON_PLACE_LIST_LENGTH;
    }
}
