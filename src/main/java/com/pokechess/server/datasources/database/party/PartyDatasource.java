package com.pokechess.server.datasources.database.party;

import com.pokechess.server.datasources.database.party.entity.PartyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface PartyDatasource extends JpaRepository<PartyEntity, Integer> {
    Optional<PartyEntity> findByName(String name);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT p FROM party_entity p WHERE p.name=:name")
    Optional<PartyEntity> findByNameWithLock(String name);
    List<PartyEntity> findAllByState(String state);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT p FROM party_entity p INNER JOIN p.players player INNER JOIN player.user user WHERE user.username=:playerUsername")
    Optional<PartyEntity> findByPlayers_User_UsernameWithLock(String playerUsername);
    Optional<PartyEntity> findByPlayers_User_Username(String playerUsername);
    boolean existsByNameAndPlayers_User_Username(String partyName, String playerName);
}
