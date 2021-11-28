package com.pokechess.server.repositories.party;

import com.pokechess.server.datasources.database.party.PartyDatasource;
import com.pokechess.server.datasources.database.party.entity.PartyEntity;
import com.pokechess.server.datasources.database.party.mapper.PartyEntityMapper;
import com.pokechess.server.datasources.database.player.mapper.PlayerEntityMapper;
import com.pokechess.server.exceptions.PartyException;
import com.pokechess.server.models.enumerations.PartyState;
import com.pokechess.server.models.party.Party;
import com.pokechess.server.models.party.Player;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.pokechess.server.models.party.Party.MAX_PLAYER;

@Repository
public class PartyRepository {
    private final PartyDatasource partyDatasource;
    private final PasswordEncoder bcryptEncoder;

    public PartyRepository(PartyDatasource partyDatasource,
                           PasswordEncoder bcryptEncoder) {
        this.partyDatasource = partyDatasource;
        this.bcryptEncoder = bcryptEncoder;
    }

    public Party create(Party party) {
        if (this.partyDatasource.findByName(party.getName()).isPresent())
            throw PartyException.of(PartyException.PartyExceptionType.NAME_ALREADY_EXIST);
        PartyEntity partyEntityCreated =
                this.partyDatasource.save(PartyEntityMapper.mapPartyToPartyEntity(party));
        return PartyEntityMapper.mapPartyFromPartyEntityWithoutGameObject(partyEntityCreated);
    }

    public List<Party> getPartyListByState(PartyState state) {
        return this.partyDatasource.findAllByState(state.name())
                .stream().map(PartyEntityMapper::mapPartyFromPartyEntityWithoutGameObject)
                .collect(Collectors.toList());
    }

    public boolean existsByNameAndPlayerName(String partyName, String playerName) {
        return this.partyDatasource.existsByNameAndPlayers_User_Username(partyName, playerName);
    }

    public boolean existsByNameAndPlayerNameAndState(String partyName, String playerName, PartyState state) {
        return this.partyDatasource.existsByNameAndPlayers_User_UsernameAndState(partyName, playerName, state.name());
    }

    @Transactional
    public Party addPlayer(String partyName, Player player, String password) {
        PartyEntity partyEntity = this.partyDatasource.findByNameWithLock(partyName)
                .orElseThrow(() -> PartyException.of(PartyException.PartyExceptionType.PARTY_NOT_FOUND));
        if (Objects.nonNull(partyEntity.getPassword()) && (Objects.isNull(password) ||
                !this.bcryptEncoder.matches(password, partyEntity.getPassword()))) {
            throw PartyException.of(PartyException.PartyExceptionType.INCORRECT_PASSWORD);
        }
        if (MAX_PLAYER <= partyEntity.getPlayers().size()) {
            throw PartyException.of(PartyException.PartyExceptionType.PARTY_MAX_PLAYER);
        }
        partyEntity.getPlayers().add(PlayerEntityMapper.mapPlayerToPlayerEntity(player));
        PartyEntity partyEntityUpdated = this.partyDatasource.save(partyEntity);
        return PartyEntityMapper.mapPartyFromPartyEntityWithoutGameObject(partyEntityUpdated);
    }

    @Transactional
    public Party deletePartyByPlayerNameAndState(String playerUsername, PartyState state) {
        PartyEntity playerParty = this.partyDatasource.findByPlayers_User_UsernameAndStateWithLock(playerUsername, state.name())
                .orElseThrow(() -> PartyException.of(PartyException.PartyExceptionType.PARTY_NOT_FOUND));
        if (playerParty.getOwner().getUsername().equals(playerUsername)) {
            this.partyDatasource.delete(playerParty);
            playerParty.setState(PartyState.DELETED.toString());
            return PartyEntityMapper.mapPartyFromPartyEntityWithoutGameObject(playerParty);
        } else {
            Party partyUpdated = PartyEntityMapper.mapPartyFromPartyEntityWithoutGameObject(playerParty);
            playerParty.getPlayers().removeIf(player -> player.getUser().getUsername().equals(playerUsername));
            this.partyDatasource.save(playerParty);
            return partyUpdated;
        }
    }
}
