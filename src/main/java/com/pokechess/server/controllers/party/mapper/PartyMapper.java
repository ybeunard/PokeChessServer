package com.pokechess.server.controllers.party.mapper;

import com.pokechess.server.controllers.party.dto.party.board.game.PokemonInstanceDTO;
import com.pokechess.server.controllers.party.dto.party.board.game.PokemonPlaceDTO;
import com.pokechess.server.controllers.party.dto.party.creation.PartyCreationResponseDTO;
import com.pokechess.server.controllers.party.dto.party.list.PartyInCreationDTO;
import com.pokechess.server.controllers.party.dto.party.list.PartyListInCreationResponseDTO;
import com.pokechess.server.controllers.pokemon.mapper.PokemonDTOMapper;
import com.pokechess.server.models.party.Party;
import com.pokechess.server.models.party.PokemonPlace;
import com.pokechess.server.models.party.instances.PokemonInstance;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
        dto.setOwner(model.getOwner().getTrainerName());
        dto.setNumberOfPlayer(model.getPlayers().size());
        dto.setWithPassword(Objects.nonNull(model.getPassword()));
        return dto;
    }

    public static PokemonPlaceDTO mapPokemonPlaceToPokemonPlaceDTO(PokemonPlace model) {
        PokemonPlaceDTO dto = new PokemonPlaceDTO();
        dto.setPosition(model.getPosition());
        Optional.ofNullable(model.getPokemon())
                .map(PartyMapper::mapPokemonInstanceToPokemonInstanceDTO).ifPresent(dto::setPokemon);
        return dto;
    }

    public static PokemonInstanceDTO mapPokemonInstanceToPokemonInstanceDTO(PokemonInstance model) {
        PokemonInstanceDTO dto = new PokemonInstanceDTO();
        dto.setPokemon(PokemonDTOMapper.mapPokemonToPokemonDTO(model.getPokemon()));
        return dto;
    }
}
