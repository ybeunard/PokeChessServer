package com.pokechess.server.datasources.database.party.mapper;

import com.pokechess.server.datasources.database.card.pokemon.entity.PokemonEntity;
import com.pokechess.server.datasources.database.party.entity.PartyEntity;
import com.pokechess.server.datasources.database.party.entity.PokemonDrawEntity;
import com.pokechess.server.datasources.database.player.mapper.PlayerEntityMapper;
import com.pokechess.server.datasources.database.user.mapper.UserEntityMapper;
import com.pokechess.server.models.enumerations.PartyState;
import com.pokechess.server.models.party.Party;

import java.util.List;
import java.util.stream.Collectors;

public class PartyEntityMapper {
    public static Party mapPartyFromPartyEntityWithoutGameObject(PartyEntity entity) {
        return Party.builder().id(entity.getId())
                .owner(UserEntityMapper.mapUserFromUserEntity(entity.getOwner()))
                .players(PlayerEntityMapper.mapPlayerListFromPlayerEntityListWithoutGameObject(entity.getPlayers()))
                .state(PartyState.getEnum(entity.getState()))
                .name(entity.getName()).password(entity.getPassword())
                .currentTurnNumber(entity.getCurrentTurnNumber()).build();
    }

    public static Party mapPartyFromPartyEntity(PartyEntity entity) {
        return Party.builder().id(entity.getId())
                .owner(UserEntityMapper.mapUserFromUserEntity(entity.getOwner()))
                .players(PlayerEntityMapper.mapPlayerListFromPlayerEntityList(entity.getPlayers()))
                .state(PartyState.getEnum(entity.getState()))
                .name(entity.getName()).password(entity.getPassword())
                .currentTurnNumber(entity.getCurrentTurnNumber()).build();
    }

    public static PartyEntity mapPartyToPartyEntity(Party model) {
        PartyEntity entity = new PartyEntity();
        entity.setId(model.getId());
        entity.setOwner(UserEntityMapper.mapUserToUserEntity(model.getOwner()));
        entity.setName(model.getName());
        entity.setPassword(model.getPassword());
        entity.setPlayers(PlayerEntityMapper.mapPlayerListToPlayerEntityList(model.getPlayers()));
        entity.setState(model.getState().name());
        entity.setCurrentTurnNumber(model.getCurrentTurnNumber());
        return entity;
    }

    public static List<PokemonDrawEntity> mapPokemonEntityListToPokemonDrawEntityList(List<PokemonEntity> pokemonEntities, PartyEntity party) {
        return pokemonEntities.stream().map(pokemonEntity -> mapPokemonEntityToPokemonDrawEntity(pokemonEntity, party)).collect(Collectors.toList());
    }

    public static PokemonDrawEntity mapPokemonEntityToPokemonDrawEntity(PokemonEntity pokemonEntity, PartyEntity party) {
        PokemonDrawEntity entity = new PokemonDrawEntity();
        entity.setParty(party);
        entity.setLevel(pokemonEntity.getLevel());
        entity.setPokemon(pokemonEntity);
        return entity;
    }
}
