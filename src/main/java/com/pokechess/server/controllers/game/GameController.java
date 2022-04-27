package com.pokechess.server.controllers.game;

import com.pokechess.server.controllers.game.dto.message.BuyMessageDTO;
import com.pokechess.server.controllers.game.dto.message.MoveMessageDTO;
import com.pokechess.server.controllers.game.dto.message.SellMessageDTO;
import com.pokechess.server.exceptions.ApiException;
import com.pokechess.server.models.enumerations.BoardPlace;
import com.pokechess.server.services.game.GameService;
import com.pokechess.server.validators.message.MessageValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Objects;

@Controller
public class GameController {
    private static final Logger LOGGER = LogManager.getLogger(GameController.class);

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @MessageMapping("/parties/{partyName}/start")
    public void startGame(@DestinationVariable String partyName,
                          Principal principal) {
        try {
            this.gameService.start(partyName, principal.getName());
        } catch (ApiException e) {
            LOGGER.warn("startGame: {}", e.getMessage());
        }
    }

    @MessageMapping("/parties/{partyName}/players/myself/load")
    public void hasLoadGame(@DestinationVariable String partyName,
                          Principal principal) {
        try {
            this.gameService.hasLoadGame(partyName, principal.getName());
        } catch (ApiException e) {
            LOGGER.warn("hasLoadGame: {}", e.getMessage());
        }
    }

    @MessageMapping("/parties/{partyName}/players/myself/roll")
    public void rollHand(@DestinationVariable String partyName,
                         Principal principal) {
        try {
            this.gameService.rollHand(partyName, principal.getName());
        } catch (ApiException e) {
            LOGGER.warn("rollHand: {}", e.getMessage());
        }
    }

    @MessageMapping("/parties/{partyName}/players/myself/lock")
    public void rollHand(Principal principal) {
        try {
            this.gameService.changeLockHand(principal.getName());
        } catch (ApiException e) {
            LOGGER.warn("rollHand: {}", e.getMessage());
        }
    }

    @MessageMapping("/parties/{partyName}/players/myself/experience")
    public void buyExperience(@DestinationVariable String partyName,
                         Principal principal) {
        try {
            this.gameService.buyExperience(partyName, principal.getName());
        } catch (ApiException e) {
            LOGGER.warn("buyExperience: {}", e.getMessage());
        }
    }

    @MessageMapping("/parties/{partyName}/players/myself/buy")
    public void buyCardHand(@DestinationVariable String partyName,
                          Principal principal, BuyMessageDTO message) {
        if (Objects.isNull(message) || Objects.isNull(message.getId())) {
            LOGGER.warn("buyCardHand: Incorrect message dto: {}", message);
            return;
        }
        try {
            this.gameService.buyPokemonCard(message.getId(), partyName, principal.getName());
        } catch (ApiException e) {
            LOGGER.warn("buyCardHand: {}", e.getMessage());
        }
    }

    @MessageMapping("/parties/{partyName}/players/myself/move")
    public void movePokemon(@DestinationVariable String partyName,
                          Principal principal, MoveMessageDTO message) {
        if (!MessageValidator.validateMoveMessageDTO(message)) {
            LOGGER.warn("movePokemon: Incorrect message dto: {}", message);
            return;
        }
        try {
            this.gameService.movePokemon(partyName, principal.getName(), message.getId(), BoardPlace.getEnum(message.getPlace()), message.getPosition(),
                    BoardPlace.getEnum(message.getDestinationPlace()), message.getDestinationPosition());
        } catch (ApiException e) {
            LOGGER.warn("movePokemon: {}", e.getMessage());
        }
    }

    @MessageMapping("/parties/{partyName}/players/myself/sell")
    public void sellPokemon(@DestinationVariable String partyName,
                          Principal principal, SellMessageDTO message) {
        if (!MessageValidator.validateSellMessageDTO(message)) {
            LOGGER.warn("sellPokemon: Incorrect message dto: {}", message);
            return;
        }
        try {
            this.gameService.sellPokemon(partyName, principal.getName(), message.getId(),
                    BoardPlace.getEnum(message.getPlace()), message.getPosition());
        } catch (ApiException e) {
            LOGGER.warn("sellPokemon: {}", e.getMessage());
        }
    }
}
