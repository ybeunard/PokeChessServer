package com.pokechess.server.repositories.party;

import com.pokechess.server.datasources.database.board.game.entity.PokemonInstanceEntity;
import com.pokechess.server.datasources.database.board.game.entity.PokemonPlaceEntity;
import com.pokechess.server.datasources.database.board.game.mapper.BoardGameEntityMapper;
import com.pokechess.server.datasources.database.card.pokemon.PokemonDatasource;
import com.pokechess.server.datasources.database.card.pokemon.entity.PokemonEntity;
import com.pokechess.server.datasources.database.card.pokemon.mapper.PokemonEntityMapper;
import com.pokechess.server.datasources.database.party.PartyDatasource;
import com.pokechess.server.datasources.database.party.PokemonDrawDatasource;
import com.pokechess.server.datasources.database.party.entity.PartyEntity;
import com.pokechess.server.datasources.database.party.entity.PokemonDrawEntity;
import com.pokechess.server.datasources.database.party.mapper.PartyEntityMapper;
import com.pokechess.server.datasources.database.player.PlayerDatasource;
import com.pokechess.server.datasources.database.player.entity.PlayerEntity;
import com.pokechess.server.datasources.database.player.mapper.PlayerEntityMapper;
import com.pokechess.server.exceptions.GameException;
import com.pokechess.server.exceptions.PartyException;
import com.pokechess.server.exceptions.UserException;
import com.pokechess.server.models.enumerations.BoardPlace;
import com.pokechess.server.models.enumerations.PartyState;
import com.pokechess.server.models.globals.game.cards.Pokemon;
import com.pokechess.server.models.party.Party;
import com.pokechess.server.models.party.Player;
import com.pokechess.server.models.party.instances.PokemonInstance;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.pokechess.server.models.enumerations.PartyState.PARTY_STATE_TRANSITION_MAP;
import static com.pokechess.server.models.enumerations.PartyState.ADD_PLAYER_PARTY_STATE;
import static com.pokechess.server.models.party.BoardGame.POKEMON_STARTING_POSITION;
import static com.pokechess.server.models.party.Party.*;

@Repository
public class PartyRepository {

    private final PartyDatasource partyDatasource;
    private final PokemonDatasource pokemonDatasource;
    private final PokemonDrawDatasource pokemonDrawDatasource;
    private final PlayerDatasource playerDatasource;
    private final PasswordEncoder bcryptEncoder;

    public PartyRepository(PartyDatasource partyDatasource,
                           PokemonDatasource pokemonDatasource,
                           PokemonDrawDatasource pokemonDrawDatasource,
                           PlayerDatasource PlayerDatasource,
                           PasswordEncoder bcryptEncoder) {
        this.partyDatasource = partyDatasource;
        this.pokemonDatasource = pokemonDatasource;
        this.pokemonDrawDatasource = pokemonDrawDatasource;
        this.playerDatasource = PlayerDatasource;
        this.bcryptEncoder = bcryptEncoder;
    }

    public Party create(Party party) {
        if (this.partyDatasource.findByName(party.getName()).isPresent())
            throw PartyException.of(PartyException.PartyExceptionType.NAME_ALREADY_EXIST);
        PartyEntity partyEntityCreated =
                this.partyDatasource.save(PartyEntityMapper.mapPartyToPartyEntity(party));
        return PartyEntityMapper.mapPartyFromPartyEntityWithoutGameObject(partyEntityCreated);
    }

    @Transactional
    public Party getPartyWithoutGameObjectByName(String partyName) {
        return this.partyDatasource.findByName(partyName)
                .map(PartyEntityMapper::mapPartyFromPartyEntityWithoutGameObject)
                .orElseThrow(() -> PartyException.of(PartyException.PartyExceptionType.PARTY_NOT_FOUND));
    }

    @Transactional
    public Party getPartyByName(String partyName) {
        return this.partyDatasource.findByName(partyName)
                .map(PartyEntityMapper::mapPartyFromPartyEntity)
                .orElseThrow(() -> PartyException.of(PartyException.PartyExceptionType.PARTY_NOT_FOUND));
    }

    @Transactional
    public Party getPartyByPlayerName(String playerUsername) {
        return this.partyDatasource.findByPlayers_User_Username(playerUsername)
                .map(PartyEntityMapper::mapPartyFromPartyEntityWithoutGameObject)
                .orElseThrow(() -> PartyException.of(PartyException.PartyExceptionType.PARTY_NOT_FOUND));
    }

    @Transactional
    public List<Party> getPartyListByState(PartyState state) {
        return this.partyDatasource.findAllByState(state.name())
                .stream().map(PartyEntityMapper::mapPartyFromPartyEntityWithoutGameObject)
                .collect(Collectors.toList());
    }

    public boolean existsByNameAndPlayerName(String partyName, String playerName) {
        return this.partyDatasource.existsByNameAndPlayers_User_Username(partyName, playerName);
    }

    @Transactional
    public Party addPlayer(String partyName, Player player, String password) {
        PartyEntity partyEntity = this.partyDatasource.findByNameWithLock(partyName)
                .orElseThrow(() -> PartyException.of(PartyException.PartyExceptionType.PARTY_NOT_FOUND));
        if (!ADD_PLAYER_PARTY_STATE.contains(PartyState.getEnum(partyEntity.getState())))
            throw PartyException.of(PartyException.PartyExceptionType.PARTY_ALREADY_START);
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
    public Party deletePartyByPlayerNameAndState(String playerUsername) {
        PartyEntity playerParty = this.partyDatasource.findByPlayers_User_UsernameWithLock(playerUsername)
                .orElseThrow(() -> PartyException.of(PartyException.PartyExceptionType.PARTY_NOT_FOUND));
        // If creation mode
        if (PARTY_STATE_TRANSITION_MAP.get(PartyState.DELETED).contains(PartyState.getEnum(playerParty.getState()))) {
            // If the owner leave then delete the party
            if (playerParty.getOwner().getUsername().equals(playerUsername)) {
                this.pokemonDrawDatasource.deleteByPartyId(playerParty.getId());
                this.partyDatasource.delete(playerParty);
                playerParty.setState(PartyState.DELETED.name());

                // Else the player leave the party
            } else {
                playerParty.getPlayers().removeIf(player -> player.getUser().getUsername().equals(playerUsername));
                this.partyDatasource.save(playerParty);
            }
            return PartyEntityMapper.mapPartyFromPartyEntityWithoutGameObject(playerParty);

            // If the party is running the player is marked as disconnected, if all player are disconnected the party is terminated
        } else if (PARTY_STATE_TRANSITION_MAP.get(PartyState.TERMINATED).contains(PartyState.getEnum(playerParty.getState()))) {
            playerParty.getPlayers().stream().filter(player -> playerUsername.equals(player.getUser().getUsername()))
                    .forEach(player -> player.setDisconnected(true));
            if (playerParty.getPlayers().stream().allMatch(PlayerEntity::isDisconnected)) {
                this.pokemonDrawDatasource.deleteByPartyId(playerParty.getId());
                this.partyDatasource.delete(playerParty);
                playerParty.setState(PartyState.TERMINATED.name());
            } else {
                this.partyDatasource.save(playerParty);
            }
            return PartyEntityMapper.mapPartyFromPartyEntityWithoutGameObject(playerParty);
        } else {
            throw PartyException.of(PartyException.PartyExceptionType.INCORRECT_STATE);
        }
    }

    @Transactional
    public Party terminateParty(String partyName) {
        PartyEntity playerParty = this.partyDatasource.findByName(partyName)
                .orElseThrow(() -> PartyException.of(PartyException.PartyExceptionType.PARTY_NOT_FOUND));
        if (PARTY_STATE_TRANSITION_MAP.get(PartyState.TERMINATED).contains(PartyState.getEnum(playerParty.getState())))
            throw PartyException.of(PartyException.PartyExceptionType.PARTY_NOT_START);
        this.pokemonDrawDatasource.deleteByPartyId(playerParty.getId());
        this.partyDatasource.delete(playerParty);
        playerParty.setState(PartyState.TERMINATED.name());
        return PartyEntityMapper.mapPartyFromPartyEntityWithoutGameObject(playerParty);
    }

    @Transactional
    public void startParty(String partyName) {
        PartyEntity partyEntity = this.partyDatasource.findByNameWithLock(partyName)
                .orElseThrow(() -> PartyException.of(PartyException.PartyExceptionType.PARTY_NOT_FOUND));
        if (!PARTY_STATE_TRANSITION_MAP.get(PartyState.STARTED).contains(PartyState.getEnum(partyEntity.getState())))
            throw PartyException.of(PartyException.PartyExceptionType.PARTY_ALREADY_START);
        if (MIN_PLAYER > partyEntity.getPlayers().size())
            throw PartyException.of(PartyException.PartyExceptionType.PARTY_MIN_PLAYER);
        List<PokemonEntity> pokemonEntities = this.pokemonDatasource.findAllBaseEvolved();
        this.pokemonDrawDatasource.saveAll(PartyEntityMapper.mapPokemonEntityListToPokemonDrawEntityList(pokemonEntities, partyEntity));
        partyEntity.setState(PartyState.STARTED.name());
        this.partyDatasource.save(partyEntity);
    }

    @Transactional
    public Party changePartyState(String partyName, PartyState state) {
        PartyEntity partyEntity = this.partyDatasource.findByNameWithLock(partyName)
                .orElseThrow(() -> PartyException.of(PartyException.PartyExceptionType.PARTY_NOT_FOUND));
        if (!PARTY_STATE_TRANSITION_MAP.get(state).contains(PartyState.getEnum(partyEntity.getState())))
            throw PartyException.of(PartyException.PartyExceptionType.INCORRECT_STATE);
        partyEntity.setState(state.name());
        return PartyEntityMapper.mapPartyFromPartyEntityWithoutGameObject(partyDatasource.save(partyEntity));
    }

    @Transactional
    public List<Pokemon> drawPokemon(String partyName, Integer playerId, Integer number, Integer coast) {
        PartyEntity partyEntity = this.partyDatasource.findByNameWithLock(partyName)
                .orElseThrow(() -> PartyException.of(PartyException.PartyExceptionType.PARTY_NOT_FOUND));

        if (!PartyState.DRAW_POKEMON_PARTY_STATE.contains(PartyState.getEnum(partyEntity.getState())))
            throw PartyException.of(PartyException.PartyExceptionType.INCORRECT_STATE);

        PlayerEntity playerEntity = partyEntity.getPlayers().stream().filter(player -> player.getId().equals(playerId))
                .findFirst().orElseThrow(() -> UserException.of(UserException.UserExceptionType.USER_NOT_FOUND));

        if (coast > NO_COAST) {
            if (playerEntity.getMoney() < coast)
                throw GameException.of(GameException.GameExceptionType.NOT_ENOUGH_MONEY);
            playerEntity.setMoney(playerEntity.getMoney() - coast);
            this.playerDatasource.save(playerEntity);
        }

        List<PokemonEntity> entities = IntStream.range(0, number)
                .mapToObj((integer) -> this.pokemonDrawDatasource.drawPokemon(partyEntity.getId(), playerEntity.getLevel()).orElse(null))
                .filter(Objects::nonNull).map(PokemonDrawEntity::getPokemon).collect(Collectors.toList());
        return PokemonEntityMapper.mapPokemonListFromPokemonEntityList(entities);
    }

    @Transactional
    public Player buyExperience(String partyName, Integer playerId, Integer experience, Integer level, Integer coast) {
        PartyEntity partyEntity = this.partyDatasource.findByNameWithLock(partyName)
                .orElseThrow(() -> PartyException.of(PartyException.PartyExceptionType.PARTY_NOT_FOUND));
        if (!PartyState.BUY_EXPERIENCE_PARTY_STATE.contains(PartyState.getEnum(partyEntity.getState())))
            throw PartyException.of(PartyException.PartyExceptionType.INCORRECT_STATE);
        PlayerEntity playerEntity = partyEntity.getPlayers().stream().filter(player -> player.getId().equals(playerId))
                .findFirst().orElseThrow(() -> UserException.of(UserException.UserExceptionType.USER_NOT_FOUND));
        applyCoastOnPlayer(playerEntity, coast);
        playerEntity.setExperiencePoint(experience);
        playerEntity.setLevel(level);

        playerEntity = this.playerDatasource.save(playerEntity);
        return PlayerEntityMapper.mapPlayerFromPlayerEntityWithoutGameObject(playerEntity);
    }

    @Transactional
    public Player buyFirstPokemon(String partyName, Integer playerId, PokemonInstance pokemonInstance) {
        PartyEntity partyEntity = this.partyDatasource.findByNameWithLock(partyName)
                .orElseThrow(() -> PartyException.of(PartyException.PartyExceptionType.PARTY_NOT_FOUND));
        if (!PartyState.FIRST_CAROUSEL.equals(PartyState.getEnum(partyEntity.getState())))
            throw PartyException.of(PartyException.PartyExceptionType.INCORRECT_STATE);
        PlayerEntity playerEntity = partyEntity.getPlayers().stream().filter(player -> player.getId().equals(playerId))
                .findFirst().orElseThrow(() -> UserException.of(UserException.UserExceptionType.USER_NOT_FOUND));

        PokemonPlaceEntity placeEntity = playerEntity.getBoardGame().getOffensiveLine()
                .stream().filter(place -> Objects.isNull(place.getPokemonInstance()) && place.getPosition().equals(POKEMON_STARTING_POSITION))
                .findFirst().orElseThrow(() -> GameException.of(GameException.GameExceptionType.POKEMON_BENCH_PLACE_NOT_FOUND));
        movePokemonFromHandToBench(playerEntity, placeEntity, pokemonInstance);

        this.pokemonDrawDatasource.saveAll(PartyEntityMapper.mapPokemonEntityListToPokemonDrawEntityList(playerEntity.getHand(), partyEntity));
        playerEntity.setHand(new ArrayList<>());
        playerEntity = this.playerDatasource.save(playerEntity);
        return PlayerEntityMapper.mapPlayerFromPlayerEntity(playerEntity);
    }

    @Transactional
    public Player buyPokemon(String partyName, Integer playerId, Integer coast, PokemonInstance pokemonInstance) {
        PartyEntity partyEntity = this.partyDatasource.findByNameWithLock(partyName)
                .orElseThrow(() -> PartyException.of(PartyException.PartyExceptionType.PARTY_NOT_FOUND));
        if (!PartyState.BUY_POKEMON_PARTY_STATE.contains(PartyState.getEnum(partyEntity.getState())))
            throw PartyException.of(PartyException.PartyExceptionType.INCORRECT_STATE);
        PlayerEntity playerEntity = partyEntity.getPlayers().stream().filter(player -> player.getId().equals(playerId))
                .findFirst().orElseThrow(() -> UserException.of(UserException.UserExceptionType.USER_NOT_FOUND));

        // Pay the Pokemon
        applyCoastOnPlayer(playerEntity, coast);

        PokemonPlaceEntity placeEntity = playerEntity.getBoardGame().getBench()
                .stream().filter(place -> Objects.isNull(place.getPokemonInstance()))
                .findFirst().orElseThrow(() -> GameException.of(GameException.GameExceptionType.POKEMON_BENCH_PLACE_NOT_FOUND));
        movePokemonFromHandToBench(playerEntity, placeEntity, pokemonInstance);

        playerEntity = this.playerDatasource.save(playerEntity);
        return PlayerEntityMapper.mapPlayerFromPlayerEntity(playerEntity);
    }

    @Transactional
    public Player movePokemon(String partyName, Integer playerId, String pokemonId, BoardPlace place, Integer position,
                              BoardPlace destinationPlace, Integer destinationPosition) {
        PartyEntity partyEntity = this.partyDatasource.findByNameWithLock(partyName)
                .orElseThrow(() -> PartyException.of(PartyException.PartyExceptionType.PARTY_NOT_FOUND));
        if (!PartyState.MOVE_POKEMON_PARTY_STATE.contains(PartyState.getEnum(partyEntity.getState())))
            throw PartyException.of(PartyException.PartyExceptionType.INCORRECT_STATE);

        PlayerEntity playerEntity = partyEntity.getPlayers().stream().filter(player -> player.getId().equals(playerId))
                .findFirst().orElseThrow(() -> UserException.of(UserException.UserExceptionType.USER_NOT_FOUND));

        // Get the Pokemon to move
        PokemonPlaceEntity pokemonPlaceEntity = switch (place) {
            case OFFENSIVE -> getPlaceFromPokemonIdAndPosition(playerEntity.getBoardGame().getOffensiveLine(), pokemonId, position);
            case DEFENSIVE -> getPlaceFromPokemonIdAndPosition(playerEntity.getBoardGame().getDefensiveLine(), pokemonId, position);
            case BENCH -> getPlaceFromPokemonIdAndPosition(playerEntity.getBoardGame().getBench(), pokemonId, position);
            case BENCH_OVERLOAD -> {
                PokemonPlaceEntity pokemonPlaceEntityTemp = playerEntity.getBoardGame().getBenchOverload().stream()
                    .filter(placeEntity -> Objects.nonNull(placeEntity.getPokemonInstance())
                            && placeEntity.getPokemonInstance().getPokemon().getPokemonId().equals(pokemonId))
                    .findFirst().orElseThrow(() -> GameException.of(GameException.GameExceptionType.POKEMON_BOARD_NOT_FOUND));
                playerEntity.getBoardGame().getBenchOverload().remove(pokemonPlaceEntityTemp);
                yield pokemonPlaceEntityTemp;
            }
            case CENTER -> Optional.of(playerEntity.getBoardGame().getPokemonCenter())
                    .filter(placeEntity -> Objects.nonNull(placeEntity.getPokemonInstance())
                            && placeEntity.getPokemonInstance().getPokemon().getPokemonId().equals(pokemonId))
                    .orElseThrow(() -> GameException.of(GameException.GameExceptionType.POKEMON_BOARD_NOT_FOUND));
        };

        // Get the destination place
        PokemonPlaceEntity destinationPokemonPlaceEntity = switch (destinationPlace) {
            case OFFENSIVE -> getPlaceFromPosition(playerEntity.getBoardGame().getOffensiveLine(), destinationPosition);
            case DEFENSIVE -> {
                PokemonPlaceEntity placeEntity = getPlaceFromPosition(playerEntity.getBoardGame().getOffensiveLine(), destinationPosition);
                if (Objects.isNull(placeEntity.getPokemonInstance())) yield placeEntity;
                yield getPlaceFromPosition(playerEntity.getBoardGame().getDefensiveLine(), destinationPosition);
            }
            case BENCH -> getPlaceFromPosition(playerEntity.getBoardGame().getBench(), destinationPosition);
            case BENCH_OVERLOAD -> throw GameException.of(GameException.GameExceptionType.CANNOT_MOVE_ON_BENCH_OVERLOAD);
            case CENTER -> playerEntity.getBoardGame().getPokemonCenter();
        };

        // Verify don't move from overload bench to an empty place
        if (BoardPlace.BENCH_OVERLOAD.equals(place) && Objects.nonNull(destinationPokemonPlaceEntity.getPokemonInstance()))
            throw GameException.of(GameException.GameExceptionType.CANNOT_MOVE_ON_BENCH_OVERLOAD);

        PokemonInstanceEntity movedPokemonInstance = destinationPokemonPlaceEntity.getPokemonInstance();
        destinationPokemonPlaceEntity.setPokemonInstance(pokemonPlaceEntity.getPokemonInstance());
        pokemonPlaceEntity.setPokemonInstance(movedPokemonInstance);

        // If moved Pokemon is null and defensive Pokemon exist, move it on offensive line
        if (BoardPlace.OFFENSIVE.equals(place) && Objects.isNull(movedPokemonInstance)) {
            PokemonPlaceEntity pokemonPlaceEntityTemp = getPlaceFromPosition(playerEntity.getBoardGame().getDefensiveLine(), position);
            pokemonPlaceEntity.setPokemonInstance(pokemonPlaceEntityTemp.getPokemonInstance());
            pokemonPlaceEntityTemp.setPokemonInstance(null);
        }

        playerEntity = this.playerDatasource.save(playerEntity);
        return PlayerEntityMapper.mapPlayerFromPlayerEntity(playerEntity);
    }

    @Transactional
    public Player sellPokemon(String partyName, Integer playerId, String pokemonId, BoardPlace place, Integer position, Integer coast) {
        PartyEntity partyEntity = this.partyDatasource.findByNameWithLock(partyName)
                .orElseThrow(() -> PartyException.of(PartyException.PartyExceptionType.PARTY_NOT_FOUND));
        if (!PartyState.SELL_POKEMON_PARTY_STATE.contains(PartyState.getEnum(partyEntity.getState())))
            throw PartyException.of(PartyException.PartyExceptionType.INCORRECT_STATE);

        PlayerEntity playerEntity = partyEntity.getPlayers().stream().filter(player -> player.getId().equals(playerId))
                .findFirst().orElseThrow(() -> UserException.of(UserException.UserExceptionType.USER_NOT_FOUND));

        if (coast > NO_COAST) {
            playerEntity.setMoney(playerEntity.getMoney() + coast);
        }

        // Sell the Pokemon
        PokemonEntity pokemon = switch (place) {
            case OFFENSIVE -> sellPlaceFromPokemonIdAndPosition(playerEntity.getBoardGame().getOffensiveLine(),
                    playerEntity.getBoardGame().getBenchOverload(), pokemonId, position);
            case DEFENSIVE -> sellPlaceFromPokemonIdAndPosition(playerEntity.getBoardGame().getDefensiveLine(),
                    playerEntity.getBoardGame().getBenchOverload(), pokemonId, position);
            case BENCH -> sellPlaceFromPokemonIdAndPosition(playerEntity.getBoardGame().getBench(),
                    playerEntity.getBoardGame().getBenchOverload(), pokemonId, position);
            case BENCH_OVERLOAD -> {
                PokemonPlaceEntity pokemonPlaceEntity = playerEntity.getBoardGame().getBenchOverload().stream()
                        .filter(placeEntity -> Objects.nonNull(placeEntity.getPokemonInstance())
                                && placeEntity.getPokemonInstance().getPokemon().getPokemonId().equals(pokemonId))
                        .findFirst().orElseThrow(() -> GameException.of(GameException.GameExceptionType.POKEMON_BOARD_NOT_FOUND));
                playerEntity.getBoardGame().getBenchOverload().remove(pokemonPlaceEntity);
                yield pokemonPlaceEntity.getPokemonInstance().getPokemon();
            }
            case CENTER -> {
                PokemonPlaceEntity pokemonPlaceEntity = Optional.of(playerEntity.getBoardGame().getPokemonCenter())
                        .filter(placeEntity -> Objects.nonNull(placeEntity.getPokemonInstance())
                                && placeEntity.getPokemonInstance().getPokemon().getPokemonId().equals(pokemonId))
                        .orElseThrow(() -> GameException.of(GameException.GameExceptionType.POKEMON_BOARD_NOT_FOUND));
                pokemonPlaceEntity.setPokemonInstance(null);
                yield pokemonPlaceEntity.getPokemonInstance().getPokemon();
            }
        };

        if (BoardPlace.OFFENSIVE.equals(place)
                && Objects.isNull(getPlaceFromPosition(playerEntity.getBoardGame().getOffensiveLine(), position).getPokemonInstance())) {
            getPlaceFromPosition(playerEntity.getBoardGame().getOffensiveLine(), position)
                    .setPokemonInstance(getPlaceFromPosition(playerEntity.getBoardGame().getDefensiveLine(), position).getPokemonInstance());
        }

        this.pokemonDrawDatasource.save(PartyEntityMapper.mapPokemonEntityToPokemonDrawEntity(pokemon, partyEntity));
        playerEntity = this.playerDatasource.save(playerEntity);
        return PlayerEntityMapper.mapPlayerFromPlayerEntity(playerEntity);
    }

    @Transactional
    public void incrementPartyCurrentTurnNumber(Integer partyId) {
        PartyEntity party = this.partyDatasource.findById(partyId)
                .orElseThrow(() -> PartyException.of(PartyException.PartyExceptionType.PARTY_NOT_FOUND));
        if (!PartyState.INCREMENT_TURN_POKEMON_PARTY_STATE.contains(PartyState.getEnum(party.getState())))
            throw PartyException.of(PartyException.PartyExceptionType.INCORRECT_STATE);
        party.setCurrentTurnNumber(party.getCurrentTurnNumber() + 1);
        this.partyDatasource.save(party);
    }

    private void applyCoastOnPlayer(PlayerEntity player, Integer coast) {
        if (coast > NO_COAST) {
            if (player.getMoney() < coast)
                throw GameException.of(GameException.GameExceptionType.NOT_ENOUGH_MONEY);
            player.setMoney(player.getMoney() - coast);
        }
    }

    private void movePokemonFromHandToBench(PlayerEntity player, PokemonPlaceEntity place, PokemonInstance pokemon) {
        for (int i = 0; i < player.getHand().size(); i++) {
            if (player.getHand().get(i).getPokemonId()
                    .equals(pokemon.getPokemon().getPokemonId())) {
                place.setPokemonInstance(
                        BoardGameEntityMapper.mapPokemonInstanceToPokemonInstanceEntity(pokemon));
                player.getHand().remove(i);
                break;
            }
        }
    }

    private PokemonEntity sellPlaceFromPokemonIdAndPosition(List<PokemonPlaceEntity> places,
                                                                 List<PokemonPlaceEntity> placesOverload, String pokemonId, Integer position) {
        PokemonPlaceEntity place = getPlaceFromPokemonIdAndPosition(places, pokemonId, position);
        PokemonEntity pokemon = place.getPokemonInstance().getPokemon();
        if (!placesOverload.isEmpty()) {
            place.setPokemonInstance(placesOverload.get(0).getPokemonInstance());
            placesOverload.remove(0);
        } else {
            place.setPokemonInstance(null);
        }
        return pokemon;
    }

    private PokemonPlaceEntity getPlaceFromPokemonIdAndPosition(List<PokemonPlaceEntity> places, String pokemonId, Integer position) {
        PokemonPlaceEntity place = getPlaceFromPosition(places, position);
        if (Objects.isNull(place.getPokemonInstance()) || place.getPokemonInstance().getPokemon().getPokemonId().equals(pokemonId))
            throw GameException.of(GameException.GameExceptionType.POKEMON_BOARD_NOT_FOUND);
        return place;
    }

    private PokemonPlaceEntity getPlaceFromPosition(List<PokemonPlaceEntity> places, Integer position) {
        return places.stream().filter(place -> place.getPosition().equals(position))
                .findFirst().orElseThrow(() -> GameException.of(GameException.GameExceptionType.POKEMON_BOARD_NOT_FOUND));
    }
}
