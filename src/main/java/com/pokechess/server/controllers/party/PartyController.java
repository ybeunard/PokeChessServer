package com.pokechess.server.controllers.party;

import com.pokechess.server.controllers.party.dto.party.creation.PartyCreationRequestDTO;
import com.pokechess.server.controllers.party.dto.party.creation.PartyCreationResponseDTO;
import com.pokechess.server.controllers.party.mapper.PartyMapper;
import com.pokechess.server.exceptions.UserException;
import com.pokechess.server.models.party.Party;
import com.pokechess.server.services.party.PartyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.pokechess.server.filter.security.JwtRequestFilter.REQUEST_USERNAME_ATTRIBUTE;

@Controller
public class PartyController {
    private final PartyService partyService;

    public PartyController(PartyService partyService) {
        this.partyService = partyService;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(PartyMapper.mapPartyToPartyCreationResponseDTO(partyCreated));
    }

    @DeleteMapping(value = "/api/v1/party")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void leaveParty(@RequestAttribute(REQUEST_USERNAME_ATTRIBUTE) String username) {
        this.partyService.leaveParty(username);
    }
}
