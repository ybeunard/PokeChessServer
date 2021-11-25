package com.pokechess.server.controllers.party;

import com.pokechess.server.controllers.party.dto.party.creation.PartyCreationRequestDTO;
import com.pokechess.server.controllers.party.dto.party.creation.PartyCreationResponseDTO;
import com.pokechess.server.controllers.party.dto.party.list.PartyListInCreationResponseDTO;
import com.pokechess.server.controllers.party.dto.party.password.PasswordRequestDTO;
import com.pokechess.server.controllers.party.mapper.PartyMapper;
import com.pokechess.server.exceptions.PartyException;
import com.pokechess.server.exceptions.UserException;
import com.pokechess.server.exceptions.ValidationException;
import com.pokechess.server.models.party.Party;
import com.pokechess.server.services.party.PartyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Objects;

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
     * @throws PartyException Party name need to be unique
     */
    @PostMapping(value = "/api/v1/party")
    public ResponseEntity<PartyCreationResponseDTO> createParty(@RequestAttribute(REQUEST_USERNAME_ATTRIBUTE) String username,
                                                                @RequestBody @Valid PartyCreationRequestDTO requestDTO) {
        if (Objects.nonNull(requestDTO.getPassword()) && "".equals(requestDTO.getPassword())) {
            throw new ValidationException("Password", "Password can null but not empty");
        }
        Party partyCreated = this.partyService.createParty(username, requestDTO.getName(), requestDTO.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(PartyMapper.mapPartyToPartyCreationResponseDTO(partyCreated));
    }

    /**
     *
     * @throws UserException User already in game exception, User cannot create a party if already in game
     */
    @GetMapping(value = "/api/v1/party")
    public ResponseEntity<PartyListInCreationResponseDTO> getPartyListInCreation(@RequestAttribute(REQUEST_USERNAME_ATTRIBUTE) String username) {
        List<Party> partyList = this.partyService.getPartyListInCreation(username);
        return ResponseEntity.ok(PartyMapper.mapPartyListToPartyListInCreationResponseDTO(partyList));
    }

    /**
     *
     * @throws UserException User not found exception
     * @throws UserException User already in game exception, User cannot create a party if already in game
     * @throws PartyException Party not found exception
     * @throws PartyException Incorrect password provided
     * @throws PartyException Max player in the game, Cannot exceed a number of player by game
     */
    @PostMapping(value = "/api/v1/party/{partyName}/join")
    public ResponseEntity<PartyCreationResponseDTO> joinParty(@RequestAttribute(REQUEST_USERNAME_ATTRIBUTE) String username,
                                    @PathVariable String partyName, @RequestBody(required = false) PasswordRequestDTO requestDTO) {
        Party partyUpdated = this.partyService.joinParty(username, partyName, Objects.nonNull(requestDTO) ? requestDTO.getPassword() : null);
        return ResponseEntity.ok(PartyMapper.mapPartyToPartyCreationResponseDTO(partyUpdated));
    }

    @DeleteMapping(value = "/api/v1/party")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void leaveParty(@RequestAttribute(REQUEST_USERNAME_ATTRIBUTE) String username) {
        this.partyService.leaveParty(username);
    }
}
