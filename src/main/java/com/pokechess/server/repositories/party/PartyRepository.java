package com.pokechess.server.repositories.party;

import com.pokechess.server.datasources.database.party.PartyDatasource;
import com.pokechess.server.datasources.database.party.entity.PartyEntity;
import com.pokechess.server.datasources.database.party.mapper.PartyEntityMapper;
import com.pokechess.server.datasources.loader.ElementLoader;
import com.pokechess.server.datasources.loader.mapper.ObjectMapper;
import com.pokechess.server.datasources.loader.mapper.PokemonMapper;
import com.pokechess.server.datasources.sender.MessageSenderDatasource;
import com.pokechess.server.datasources.sender.mapper.party.PartyMessageMapper;
import com.pokechess.server.exceptions.PartyException;
import com.pokechess.server.models.enumerations.PartyState;
import com.pokechess.server.models.globals.game.cards.*;
import com.pokechess.server.models.party.Party;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.pokechess.server.services.security.SubscriptionRegistryService.PARTY_CREATION_BROKER_DESTINATION;
import static com.pokechess.server.services.security.SubscriptionRegistryService.SPECIFIC_PARTY_UPDATE_STATE_BROKER_DESTINATION;

@Repository
public class PartyRepository {
    private final PartyDatasource partyDatasource;
    private final MessageSenderDatasource messageSenderDatasource;
    private final ElementLoader elementLoader;

    public PartyRepository(PartyDatasource partyDatasource,
                           MessageSenderDatasource messageSenderDatasource,
                           ElementLoader elementLoader) {
        this.partyDatasource = partyDatasource;
        this.messageSenderDatasource = messageSenderDatasource;
        this.elementLoader = elementLoader;
    }

    public Party create(Party party) {
        PartyEntity partyEntityCreated =
                this.partyDatasource.save(PartyEntityMapper.mapPartyToPartyEntity(party));
        return PartyEntityMapper.mapPartyFromPartyEntityWithoutGameObject(partyEntityCreated);
    }

    public List<Party> getPartyListByState(PartyState state) {
        return this.partyDatasource.findAllByState(state.name())
                .stream().map(PartyEntityMapper::mapPartyFromPartyEntityWithoutGameObject)
                .collect(Collectors.toList());
    }

    public Party getByPlayerNameAndState(String playerUsername, PartyState state) {
        return this.partyDatasource.findByPlayers_User_UsernameAndState(playerUsername, state.name())
                .map(PartyEntityMapper::mapPartyFromPartyEntityWithoutGameObject)
                .orElseThrow(() -> PartyException.of(PartyException.PartyExceptionType.PARTY_NOT_FOUND));
    }

    public boolean existsByNameAndPlayerName(String partyName, String playerName) {
        return this.partyDatasource.existsByNameAndPlayers_User_Username(partyName, playerName);
    }

    public boolean existsByNameAndPlayerNameAndState(String partyName, String playerName, PartyState state) {
        return this.partyDatasource.existsByNameAndPlayers_User_UsernameAndState(partyName, playerName, state.name());
    }

    public void deletePartyById(Integer partyId) {
        this.partyDatasource.deleteById(partyId);
    }

    public void sendPartyCreationMessage(Party party) {
        this.messageSenderDatasource.sendMessage(PARTY_CREATION_BROKER_DESTINATION,
                PartyMessageMapper.mapPartyToPartyCreationMessageDTO(party));
    }

    public void sendPartyUpdatePlayerNumberMessage(Party party) {
        this.messageSenderDatasource.sendMessage(PARTY_CREATION_BROKER_DESTINATION,
                PartyMessageMapper.mapPartyToPartyUpdatePlayerNumberMessageDTO(party));
    }

    public void sendPartyDeletedMessage(String partyName) {
        this.messageSenderDatasource.sendMessage(PARTY_CREATION_BROKER_DESTINATION,
                PartyMessageMapper.mapStringToPartyDeletedMessageDTO(partyName));
    }

    public void sendPartyUpdatePlayerMessage(Party party) {
        this.messageSenderDatasource.sendMessage(String.format(SPECIFIC_PARTY_UPDATE_STATE_BROKER_DESTINATION, party.getName()),
                PartyMessageMapper.mapPartyToPartyUpdatePlayerMessageDTO(party));
    }

    public void sendPartyChangeStateMessage(Party party) {
        this.messageSenderDatasource.sendMessage(String.format(SPECIFIC_PARTY_UPDATE_STATE_BROKER_DESTINATION, party.getName()),
                PartyMessageMapper.mapPartyStateToPartyChangeStateMessageDTO(party.getState()));
    }

    public Map<Integer, List<Pokemon>> loadPokemonDraw() {
        return Optional.ofNullable(elementLoader.loadPokemon())
                .map(pokemonLoaded -> pokemonLoaded.stream().map(PokemonMapper::mapPokemonFromPokemonDTO)
                        .collect(Collectors.groupingBy(Pokemon::getLevel)))
                .orElseThrow(); // TODO THROW
    }

    public List<TrainerObject> loadTrainerObjectDraw() {
        return Optional.ofNullable(elementLoader.loadTrainerObjects())
                .map(ObjectMapper::mapTrainerObjectListFromTrainerObjectDTOList)
                .orElseThrow(); // TODO THROW
    }

    public List<Berry> loadBerries() {
        return Optional.ofNullable(elementLoader.loadBerries())
                .map(ObjectMapper::mapBerryListFromBerryDTOList)
                .orElseThrow(); // TODO THROW
    }

    public List<PokemonTrainer> loadPokemonTrainers() {
        return Optional.ofNullable(elementLoader.loadPokemonTrainers())
                .map(ObjectMapper::mapPokemonTrainerListFromPokemonTrainerDTOList)
                .orElseThrow(); // TODO THROW
    }

    public List<Weather> loadWeathers() {
        return Optional.ofNullable(elementLoader.loadWeathers())
                .map(ObjectMapper::mapWeatherListFromWeatherDTOList)
                .orElseThrow(); // TODO THROW
    }
}
