package com.pokechess.server.repositories.player;

import com.pokechess.server.datasources.database.board.game.mapper.BoardGameEntityMapper;
import com.pokechess.server.datasources.database.card.pokemon.entity.PokemonEntity;
import com.pokechess.server.datasources.database.card.pokemon.mapper.PokemonEntityMapper;
import com.pokechess.server.datasources.database.party.PartyDatasource;
import com.pokechess.server.datasources.database.party.PokemonDrawDatasource;
import com.pokechess.server.datasources.database.party.entity.PartyEntity;
import com.pokechess.server.datasources.database.party.mapper.PartyEntityMapper;
import com.pokechess.server.datasources.database.player.PlayerDatasource;
import com.pokechess.server.datasources.database.player.entity.PlayerEntity;
import com.pokechess.server.datasources.database.player.mapper.PlayerEntityMapper;
import com.pokechess.server.exceptions.PartyException;
import com.pokechess.server.exceptions.UserException;
import com.pokechess.server.models.globals.game.cards.Pokemon;
import com.pokechess.server.models.party.Player;
import com.pokechess.server.models.party.PokemonPlace;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class PlayerRepository {
    private final PartyDatasource partyDatasource;
    private final PlayerDatasource playerDatasource;
    private final PokemonDrawDatasource pokemonDrawDatasource;

    public PlayerRepository(PartyDatasource partyDatasource, PlayerDatasource playerDatasource,
                            PokemonDrawDatasource pokemonDrawDatasource) {
        this.partyDatasource = partyDatasource;
        this.playerDatasource = playerDatasource;
        this.pokemonDrawDatasource = pokemonDrawDatasource;
    }

    public Player getPlayerWithoutGameObjectByUsername(String username) {
        return this.playerDatasource.findByUser_Username(username)
                .map(PlayerEntityMapper::mapPlayerFromPlayerEntityWithoutGameObject)
                .orElseThrow(() -> UserException.of(UserException.UserExceptionType.USER_NOT_FOUND));
    }

    @Transactional
    public void setPlayerHand(Integer playerId, List<Pokemon> pokemonList) {
        PlayerEntity player = this.playerDatasource.findById(playerId)
                .orElseThrow(() -> UserException.of(UserException.UserExceptionType.USER_NOT_FOUND));
        PartyEntity party = this.partyDatasource.findByPlayers_User_Username(player.getUser().getUsername())
                .orElseThrow(() -> PartyException.of(PartyException.PartyExceptionType.PARTY_NOT_FOUND));
        this.pokemonDrawDatasource.saveAll(PartyEntityMapper.mapPokemonEntityListToPokemonDrawEntityList(player.getHand(), party));
        player.setHand(PokemonEntityMapper.mapPokemonListToPokemonEntityList(pokemonList));
        this.playerDatasource.save(player);
    }

    @Transactional
    public void setPlayerHandLock(Integer playerId, Boolean lock) {
        PlayerEntity player = this.playerDatasource.findById(playerId)
                .orElseThrow(() -> UserException.of(UserException.UserExceptionType.USER_NOT_FOUND));
        player.setLock(lock);
        this.playerDatasource.save(player);
    }

    @Transactional
    public void setPlayerOffensiveLine(Integer playerId, List<PokemonPlace> pokemonPlaceList) {
        PlayerEntity player = this.playerDatasource.findById(playerId)
                .orElseThrow(() -> UserException.of(UserException.UserExceptionType.USER_NOT_FOUND));
        player.getBoardGame().setOffensiveLine(BoardGameEntityMapper.mapPokemonPlaceListToPokemonPlaceEntityList(pokemonPlaceList));
        this.playerDatasource.save(player);
    }

    @Transactional
    public void resetPlayerBenchOverload(Integer playerId, Integer coast) {
        PlayerEntity player = this.playerDatasource.findById(playerId)
                .orElseThrow(() -> UserException.of(UserException.UserExceptionType.USER_NOT_FOUND));
        PartyEntity party = this.partyDatasource.findByPlayers_User_Username(player.getUser().getUsername())
                .orElseThrow(() -> PartyException.of(PartyException.PartyExceptionType.PARTY_NOT_FOUND));
        List<PokemonEntity> pokemonList = player.getBoardGame().getBenchOverload().stream()
                .map(place -> place.getPokemonInstance().getPokemon()).filter(Objects::nonNull).collect(Collectors.toList());
        this.pokemonDrawDatasource.saveAll(PartyEntityMapper.mapPokemonEntityListToPokemonDrawEntityList(pokemonList, party));
        player.setMoney(player.getMoney() + coast);
        player.getBoardGame().setBenchOverload(new ArrayList<>());
        this.playerDatasource.save(player);
    }

    @Transactional
    public void setPlayerPokemonCenter(Integer playerId, PokemonPlace pokemonPlace) {
        PlayerEntity player = this.playerDatasource.findById(playerId)
                .orElseThrow(() -> UserException.of(UserException.UserExceptionType.USER_NOT_FOUND));
        player.getBoardGame().setPokemonCenter(BoardGameEntityMapper.mapPokemonPlaceToPokemonPlaceEntity(pokemonPlace));
        this.playerDatasource.save(player);
    }

    @Transactional
    public void setPlayerExperience(Integer playerId, Integer level, Integer experience) {
        PlayerEntity player = this.playerDatasource.findById(playerId)
                .orElseThrow(() -> UserException.of(UserException.UserExceptionType.USER_NOT_FOUND));
        player.setLevel(level);
        player.setExperiencePoint(experience);
        this.playerDatasource.save(player);
    }

    @Transactional
    public void setPlayerMoney(Integer playerId, Integer money) {
        PlayerEntity player = this.playerDatasource.findById(playerId)
                .orElseThrow(() -> UserException.of(UserException.UserExceptionType.USER_NOT_FOUND));
        player.setMoney(money);
        this.playerDatasource.save(player);
    }

    public Boolean existsPlayerByUsername(String username) {
        return this.playerDatasource.existsByUser_Username(username);
    }

    @Transactional
    public void hasLoadGamePlayer(String username) {
        PlayerEntity player = this.playerDatasource.findByUser_Username(username)
                .orElseThrow(() -> UserException.of(UserException.UserExceptionType.USER_NOT_FOUND));
        player.setLoading(false);
        this.playerDatasource.save(player);
    }
}
