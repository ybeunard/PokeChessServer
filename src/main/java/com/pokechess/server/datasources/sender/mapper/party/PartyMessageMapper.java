package com.pokechess.server.datasources.sender.mapper.party;

import com.pokechess.server.controllers.party.mapper.PartyMapper;
import com.pokechess.server.datasources.sender.dto.party.*;
import com.pokechess.server.models.party.Party;
import com.pokechess.server.models.party.Player;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.pokechess.server.services.game.GameThread.calculatePlayerInterest;

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

    public static PartyDeletedMessageDTO mapPartyToPartyDeletedMessageDTO(Party model) {
        PartyDeletedMessageDTO dto = new PartyDeletedMessageDTO();
        dto.setName(model.getName());
        return dto;
    }

    public static PartyUpdatePlayerConnectionMessageDTO mapPartyToPartyUpdatePlayerMessageDTO(Party model) {
        PartyUpdatePlayerConnectionMessageDTO dto = new PartyUpdatePlayerConnectionMessageDTO();
        List<String> playerNames = model.getPlayers().stream().filter(player -> !player.isDisconnected()).map(player -> player.getUser().getTrainerName())
                .collect(Collectors.toList());
        List<String> playerDisconnectedNames = model.getPlayers().stream().filter(Player::isDisconnected).map(player -> player.getUser().getTrainerName())
                .collect(Collectors.toList());
        dto.setPlayers(playerNames);
        dto.setPlayersDisconnected(playerDisconnectedNames);
        return dto;
    }

    public static PartyUpdateStateMessageDTO mapPartyToPartyChangeStateMessageDTO(Party model) {
        PartyUpdateStateMessageDTO dto = new PartyUpdateStateMessageDTO();
        dto.setState(model.getState().name());
        return dto;
    }

    public static PartyUpdateTurnMessageDTO mapPartyToPartyChangeTurnMessageDTO(Party model) {
        PartyUpdateTurnMessageDTO dto = new PartyUpdateTurnMessageDTO();
        dto.setTurn(model.getCurrentTurnNumber());
        return dto;
    }

    public static PartyUpdatePlayerLevelMessageDTO mapPlayerToPartyUpdatePlayerLevelMessageDTO(Player model) {
        PartyUpdatePlayerLevelMessageDTO dto = new PartyUpdatePlayerLevelMessageDTO();
        dto.setTrainerName(model.getUser().getTrainerName());
        dto.setLevel(model.getLevel());
        return dto;
    }

    public static PartyUpdatePlayerGoldMessageDTO mapPlayerToPartyUpdatePlayerGoldMessageDTO(Player model) {
        PartyUpdatePlayerGoldMessageDTO dto = new PartyUpdatePlayerGoldMessageDTO();
        dto.setTrainerName(model.getUser().getTrainerName());
        dto.setGoldLevel(calculatePlayerInterest(model.getMoney()));
        return dto;
    }

    public static PartyUpdatePlayerPokemonPlaceMessageDTO mapPlayerOffensiveBoardToPartyUpdatePlayerPokemonPlaceMessageDTO(Player model) {
        PartyUpdatePlayerPokemonPlaceMessageDTO dto = new PartyUpdatePlayerPokemonPlaceMessageDTO();
        dto.setTrainerName(model.getUser().getTrainerName());
        dto.setPlaces(model.getBoardGame().getOffensiveLine().stream()
                .map(PartyMapper::mapPokemonPlaceToPokemonPlaceDTO).collect(Collectors.toList()));
        return dto;
    }

    public static PartyUpdatePlayerPokemonPlaceMessageDTO mapPlayerDefensiveBoardToPartyUpdatePlayerPokemonPlaceMessageDTO(Player model) {
        PartyUpdatePlayerPokemonPlaceMessageDTO dto = new PartyUpdatePlayerPokemonPlaceMessageDTO();
        dto.setTrainerName(model.getUser().getTrainerName());
        dto.setPlaces(model.getBoardGame().getDefensiveLine().stream()
                .map(PartyMapper::mapPokemonPlaceToPokemonPlaceDTO).collect(Collectors.toList()));
        return dto;
    }

    public static PartyUpdatePlayerPokemonPlaceMessageDTO mapPlayerBenchToPartyUpdatePlayerPokemonPlaceMessageDTO(Player model) {
        PartyUpdatePlayerPokemonPlaceMessageDTO dto = new PartyUpdatePlayerPokemonPlaceMessageDTO();
        dto.setTrainerName(model.getUser().getTrainerName());
        dto.setPlaces(model.getBoardGame().getBench().stream()
                .map(PartyMapper::mapPokemonPlaceToPokemonPlaceDTO).collect(Collectors.toList()));
        dto.setPlacesOverload(model.getBoardGame().getBenchOverload().stream()
                .map(PartyMapper::mapPokemonPlaceToPokemonPlaceDTO).collect(Collectors.toList()));
        return dto;
    }

    public static PartyUpdatePlayerPokemonCenterMessageDTO mapPlayerToPartyUpdatePlayerPokemonCenterMessageDTO(Player model) {
        PartyUpdatePlayerPokemonCenterMessageDTO dto = new PartyUpdatePlayerPokemonCenterMessageDTO();
        dto.setTrainerName(model.getUser().getTrainerName());
        dto.setPokemonCenter(PartyMapper.mapPokemonPlaceToPokemonPlaceDTO(model.getBoardGame().getPokemonCenter()));
        return dto;
    }
}
