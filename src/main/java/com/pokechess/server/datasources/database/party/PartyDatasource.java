package com.pokechess.server.datasources.database.party;

import com.pokechess.server.datasources.database.party.entity.PartyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartyDatasource extends JpaRepository<PartyEntity, Integer> {
    Optional<PartyEntity> findByPlayers_User_UsernameAndState(String playerUsername, String state);
    boolean existsByNameAndPlayers_User_Username(String partyName, String playerName);
    boolean existsByNameAndPlayers_User_UsernameAndState(String partyName, String playerName, String state);
}
