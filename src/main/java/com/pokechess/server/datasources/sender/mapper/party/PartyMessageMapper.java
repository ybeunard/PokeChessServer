package com.pokechess.server.datasources.sender.mapper.party;

import com.pokechess.server.datasources.sender.dto.party.*;
import com.pokechess.server.models.enumerations.PartyState;
import com.pokechess.server.models.party.Party;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PartyMessageMapper {
    public static PartyCreationMessageDTO mapPartyToPartyCreationMessageDTO(Party model) {
        PartyCreationMessageDTO dto = new PartyCreationMessageDTO();
        dto.setName(model.getName());
        dto.setOwner(model.getOwner().getTrainerName());
        dto.setWithPassword(Objects.nonNull(model.getPassword()));
        return dto;
    }

    public static PartyUpdatePlayerNumberMessageDTO mapPartyToPartyUpdatePlayerNumberMessageDTO(Party model) {
        PartyUpdatePlayerNumberMessageDTO dto = new PartyUpdatePlayerNumberMessageDTO();
        dto.setName(model.getName());
        dto.setPlayerNumber(model.getPlayers().size());
        return dto;
    }

    public static PartyDeletedMessageDTO mapStringToPartyDeletedMessageDTO(String partyName) {
        PartyDeletedMessageDTO dto = new PartyDeletedMessageDTO();
        dto.setName(partyName);
        return dto;
    }

    public static PartyUpdatePlayerMessageDTO mapPartyToPartyUpdatePlayerMessageDTO(Party party) {
        PartyUpdatePlayerMessageDTO dto = new PartyUpdatePlayerMessageDTO();
        List<String> playerNames = party.getPlayers().stream().map(player -> player.getUser().getTrainerName())
                .collect(Collectors.toList());
        dto.setPlayers(playerNames);
        return dto;
    }

    public static PartyUpdateStateMessageDTO mapPartyStateToPartyChangeStateMessageDTO(PartyState model) {
        PartyUpdateStateMessageDTO dto = new PartyUpdateStateMessageDTO();
        dto.setState(model.name());
        return dto;
    }
}
