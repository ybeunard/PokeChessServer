package com.pokechess.server.datasources.database.party;

import com.pokechess.server.datasources.database.party.entity.PartyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyDatasource extends JpaRepository<PartyEntity, Integer> {
}
