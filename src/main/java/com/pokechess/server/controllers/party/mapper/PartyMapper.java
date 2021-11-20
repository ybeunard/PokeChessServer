package com.pokechess.server.controllers.party.mapper;

import com.pokechess.server.controllers.party.dto.party.creation.PartyCreationMessageDTO;
import com.pokechess.server.controllers.party.dto.party.creation.PartyCreationResponseDTO;
import com.pokechess.server.models.party.Party;

import java.util.Objects;
import java.util.stream.Collectors;

public class PartyMapper {
    public static PartyCreationResponseDTO mapPartyToPartyCreationResponseDTO(Party model) {
        PartyCreationResponseDTO dto = new PartyCreationResponseDTO();
        dto.setOwner(model.getOwner().getTrainerName());
        dto.setName(model.getName());
        dto.setPlayers(model.getPlayers().stream()
                .map(player -> player.getUser().getTrainerName()).collect(Collectors.toList()));
        dto.setState(model.getState().name());
        dto.setWithPassword(Objects.nonNull(model.getPassword()));
        return dto;
    }

    public static PartyCreationMessageDTO mapPartyToPartyCreationMessageDTO(Party model) {
        PartyCreationMessageDTO dto = new PartyCreationMessageDTO();
        dto.setName(model.getName());
        dto.setOwner(model.getOwner().getTrainerName());
        dto.setWithPassword(Objects.nonNull(model.getPassword()));
        return dto;
    }
}
