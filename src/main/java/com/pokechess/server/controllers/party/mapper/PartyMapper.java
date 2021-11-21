package com.pokechess.server.controllers.party.mapper;

import com.pokechess.server.controllers.party.dto.party.creation.PartyCreationResponseDTO;
import com.pokechess.server.controllers.party.dto.party.list.PartyInCreationDTO;
import com.pokechess.server.controllers.party.dto.party.list.PartyListInCreationResponseDTO;
import com.pokechess.server.models.party.Party;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PartyMapper {
    public static PartyListInCreationResponseDTO mapPartyListToPartyListInCreationResponseDTO(List<Party> modelList) {
        PartyListInCreationResponseDTO dto = new PartyListInCreationResponseDTO();
        List<PartyInCreationDTO> partyInCreationDTOList = modelList.stream()
                .map(PartyMapper::mapPartyToPartyInCreationDTO).collect(Collectors.toList());
        dto.setParties(partyInCreationDTOList);
        return dto;
    }

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

    private static PartyInCreationDTO mapPartyToPartyInCreationDTO(Party model) {
        PartyInCreationDTO dto = new PartyInCreationDTO();
        dto.setName(model.getName());
        dto.setNumberOfPlayer(model.getPlayers().size());
        dto.setWithPassword(Objects.nonNull(model.getPassword()));
        return dto;
    }
}
