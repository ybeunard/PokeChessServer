package com.pokechess.server.controllers.party;

import com.pokechess.server.controllers.party.dto.party.creation.PartyCreationRequestDTO;
import com.pokechess.server.controllers.party.dto.party.creation.PartyCreationResponseDTO;
import com.pokechess.server.controllers.party.mapper.PartyMapper;
import com.pokechess.server.exceptions.UserException;
import com.pokechess.server.models.party.Party;
import com.pokechess.server.services.party.PartyService;
import com.pokechess.server.services.security.WebsocketSubscriptionRegistryService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

import static com.pokechess.server.filter.security.JwtRequestFilter.REQUEST_USERNAME_ATTRIBUTE;
import static com.pokechess.server.services.security.WebsocketSubscriptionRegistryService.PARTY_BROKER_DESTINATION;

@Controller
public class PartyController {
    private final PartyService partyService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final WebsocketSubscriptionRegistryService websocketSubscriptionRegistryService;

    public PartyController(PartyService partyService, SimpMessagingTemplate simpMessagingTemplate,
                           WebsocketSubscriptionRegistryService websocketSubscriptionRegistryService) {
        this.partyService = partyService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.websocketSubscriptionRegistryService = websocketSubscriptionRegistryService;
    }

    /**
     *
     * @throws UserException User not found exception
     * @throws UserException User already in game exception, User cannot create a party if already in game
     */
    @PostMapping(value = "/api/v1/party")
    public ResponseEntity<PartyCreationResponseDTO> createParty(@RequestAttribute(REQUEST_USERNAME_ATTRIBUTE) String username,
                                                                @RequestBody @Valid PartyCreationRequestDTO requestDTO) {
        Party partyCreated = this.partyService.createParty(username, requestDTO.getName(), requestDTO.getPassword());
        this.websocketSubscriptionRegistryService.removeSubscription(username, PARTY_BROKER_DESTINATION);
        this.simpMessagingTemplate.convertAndSend(PARTY_BROKER_DESTINATION, PartyMapper.mapPartyToPartyCreationMessageDTO(partyCreated));
        return ResponseEntity.ok(PartyMapper.mapPartyToPartyCreationResponseDTO(partyCreated));
    }
}
