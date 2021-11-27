package com.pokechess.server.repositories.message;

import com.pokechess.server.datasources.sender.MessageSenderDatasource;
import com.pokechess.server.datasources.sender.mapper.party.PartyMessageMapper;
import com.pokechess.server.models.party.Party;
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

    public void sendPartyDeletedMessage(String partyName) {
        this.messageSenderDatasource.sendMessage(PARTY_DELETED_BROKER_DESTINATION,
                PartyMessageMapper.mapStringToPartyDeletedMessageDTO(partyName));
    }

    public void sendPartyUpdatePlayerMessage(Party party) {
        this.messageSenderDatasource.sendMessage(String.format(SPECIFIC_PARTY_UPDATE_PLAYER_BROKER_DESTINATION, party.getName()),
                PartyMessageMapper.mapPartyToPartyUpdatePlayerMessageDTO(party));
    }

    public void sendPartyChangeStateMessage(Party party) {
        this.messageSenderDatasource.sendMessage(String.format(SPECIFIC_PARTY_UPDATE_STATE_BROKER_DESTINATION, party.getName()),
                PartyMessageMapper.mapPartyStateToPartyChangeStateMessageDTO(party.getState()));
    }
}
