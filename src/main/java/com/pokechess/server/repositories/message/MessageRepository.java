package com.pokechess.server.repositories.message;

import com.pokechess.server.datasources.sender.MessageSenderDatasource;
import com.pokechess.server.datasources.sender.mapper.party.PartyMessageMapper;
import com.pokechess.server.datasources.sender.mapper.player.PlayerMessageMapper;
import com.pokechess.server.models.party.Party;
import com.pokechess.server.models.party.Player;
import org.springframework.stereotype.Repository;

import static com.pokechess.server.services.security.SessionManagerService.*;

@Repository
public class MessageRepository {
    private final MessageSenderDatasource messageSenderDatasource;

    public MessageRepository(MessageSenderDatasource messageSenderDatasource) {
        this.messageSenderDatasource = messageSenderDatasource;
    }

    public void sendPartyCreationMessage(Party party) {
        this.messageSenderDatasource.sendMessage(PARTY_CREATION_BROKER_DESTINATION,
                PartyMessageMapper.mapPartyToPartyCreationMessageDTO(party));
    }

    public void sendPartyUpdatePlayerNumberMessage(Party party) {
        this.messageSenderDatasource.sendMessage(PARTY_UPDATE_PLAYER_NUMBER_BROKER_DESTINATION,
                PartyMessageMapper.mapPartyToPartyUpdatePlayerNumberMessageDTO(party));
    }

    public void sendPartyDeletedMessage(Party party) {
        this.messageSenderDatasource.sendMessage(PARTY_DELETED_BROKER_DESTINATION,
                PartyMessageMapper.mapPartyToPartyDeletedMessageDTO(party));
    }

    public void sendPartyUpdatePlayerConnectionMessage(Party party) {
        this.messageSenderDatasource.sendMessage(String.format(SPECIFIC_PARTY_UPDATE_PLAYER_CONNECTION_BROKER_DESTINATION, party.getName()),
                PartyMessageMapper.mapPartyToPartyUpdatePlayerMessageDTO(party));
    }

    public void sendPartyChangeStateMessage(Party party) {
        this.messageSenderDatasource.sendMessage(String.format(SPECIFIC_PARTY_UPDATE_STATE_BROKER_DESTINATION, party.getName()),
                PartyMessageMapper.mapPartyToPartyChangeStateMessageDTO(party));
    }

    public void sendPartyChangeTurnMessage(Party party) {
        this.messageSenderDatasource.sendMessage(String.format(SPECIFIC_PARTY_UPDATE_TURN_NUMBER_BROKER_DESTINATION, party.getName()),
                PartyMessageMapper.mapPartyToPartyChangeTurnMessageDTO(party));
    }

    public void sendPartyUpdatePlayerLevelMessage(String partyName, Player player) {
        this.messageSenderDatasource.sendMessage(String.format(SPECIFIC_PARTY_UPDATE_PLAYER_LEVEL_BROKER_DESTINATION, partyName),
                PartyMessageMapper.mapPlayerToPartyUpdatePlayerLevelMessageDTO(player));
    }

    public void sendPlayerGoldLevelMessage(String partyName, Player player) {
        this.messageSenderDatasource.sendMessage(String.format(SPECIFIC_PARTY_UPDATE_PLAYER_GOLD_BROKER_DESTINATION, partyName),
                PartyMessageMapper.mapPlayerToPartyUpdatePlayerGoldMessageDTO(player));
    }

    public void sendPartyUpdatePlayerOffensiveMessage(String partyName, Player player) {
        this.messageSenderDatasource.sendMessage(String.format(SPECIFIC_PARTY_UPDATE_PLAYER_OFFENSIVE_BROKER_DESTINATION, partyName),
                PartyMessageMapper.mapPlayerOffensiveBoardToPartyUpdatePlayerPokemonPlaceMessageDTO(player));
    }

    public void sendPartyUpdatePlayerDefensiveMessage(String partyName, Player player) {
        this.messageSenderDatasource.sendMessage(String.format(SPECIFIC_PARTY_UPDATE_PLAYER_DEFENSIVE_BROKER_DESTINATION, partyName),
                PartyMessageMapper.mapPlayerDefensiveBoardToPartyUpdatePlayerPokemonPlaceMessageDTO(player));
    }

    public void sendPartyUpdatePlayerBenchMessage(String partyName, Player player) {
        this.messageSenderDatasource.sendMessage(String.format(SPECIFIC_PARTY_UPDATE_PLAYER_BENCH_BROKER_DESTINATION, partyName),
                PartyMessageMapper.mapPlayerBenchToPartyUpdatePlayerPokemonPlaceMessageDTO(player));
    }

    public void sendPartyUpdatePlayerPokemonCenterMessage(String partyName, Player player) {
        this.messageSenderDatasource.sendMessage(String.format(SPECIFIC_PARTY_UPDATE_PLAYER_POKEMON_CENTER_BROKER_DESTINATION, partyName),
                PartyMessageMapper.mapPlayerToPartyUpdatePlayerPokemonCenterMessageDTO(player));
    }

    public void sendPlayerExperienceMessage(Player player) {
        this.messageSenderDatasource.sendMessageToPlayer(player.getUser().getUsername(), SPECIFIC_PLAYER_UPDATE_EXPERIENCE_BROKER_DESTINATION,
                PlayerMessageMapper.mapPlayerToPlayerExperienceMessageDTO(player));
    }

    public void sendPlayerGoldMessage(Player player) {
        this.messageSenderDatasource.sendMessageToPlayer(player.getUser().getUsername(), SPECIFIC_PLAYER_UPDATE_GOLD_BROKER_DESTINATION,
                PlayerMessageMapper.mapPlayerToPlayerGoldMessageDTO(player));
    }

    public void sendPlayerHandMessage(Player player) {
        this.messageSenderDatasource.sendMessageToPlayer(player.getUser().getUsername(), SPECIFIC_PLAYER_UPDATE_HAND_BROKER_DESTINATION,
                PlayerMessageMapper.mapPlayerToPlayerHandMessageDTO(player));
    }

    public void sendPlayerHandLockMessage(Player player) {
        this.messageSenderDatasource.sendMessageToPlayer(player.getUser().getUsername(), SPECIFIC_PLAYER_UPDATE_LOCK_HAND_BROKER_DESTINATION,
                PlayerMessageMapper.mapPlayerToPlayerHandLockMessageDTO(player));
    }
}
