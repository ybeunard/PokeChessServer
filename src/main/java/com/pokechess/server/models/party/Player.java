package com.pokechess.server.models.party;

import com.pokechess.server.models.globals.user.User;
import com.pokechess.server.models.globals.game.cards.Pokemon;
import com.pokechess.server.models.globals.game.cards.PokemonTrainer;
import com.pokechess.server.validators.GenericValidator;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Player {
    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer DEFAULT_EXPERIENCE_POINT = 0;
    private static final Integer DEFAULT_LIFE_POINT = 100;
    private static final Integer DEFAULT_WIN_COUNTER = 0;
    private static final Integer DEFAULT_MONEY = 0;
    private static final Boolean DEFAULT_LOCK_HAND = false;
    public static final List<Integer> LEVEL_STAGE = Arrays.asList(0, 1, 2, 4, 8, 16, 32, 56, 88, 128);
    public static final List<Integer> SERIES_GOLD = Arrays.asList(0, 0, 1, 1, 2, 3);

    private Integer id;
    private User user;
    private BoardGame boardGame;
    private Integer level;
    private Integer experiencePoint;
    private Integer lifePoint;
    private Integer winCounter;
    private List<Pokemon> hand;
    private Boolean lock;
    private Integer money;
    private Boolean loading;
    private Boolean disconnected;
    private PokemonTrainer pokemonTrainer;
    private Player opponentPlayer;

    private Player() { }

    public static PlayerBuilder builder() {
        return new PlayerBuilder();
    }

    public static class PlayerBuilder {
        private Integer id;
        private User user;
        private BoardGame boardGame;
        private Integer level;
        private Integer experiencePoint;
        private Integer lifePoint;
        private Integer winCounter;
        private List<Pokemon> hand;
        private Boolean lock;
        private Integer money;
        private Boolean loading;
        private Boolean disconnected;
        private PokemonTrainer pokemonTrainer;
        private Player opponentPlayer;

        public PlayerBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public PlayerBuilder user(User user) {
            this.user = user;
            return this;
        }

        public PlayerBuilder boardGame(BoardGame boardGame) {
            this.boardGame = boardGame;
            return this;
        }

        public PlayerBuilder level(Integer level) {
            this.level = level;
            return this;
        }

        public PlayerBuilder experiencePoint(Integer experiencePoint) {
            this.experiencePoint = experiencePoint;
            return this;
        }

        public PlayerBuilder lifePoint(Integer lifePoint) {
            this.lifePoint = lifePoint;
            return this;
        }

        public PlayerBuilder winCounter(Integer winCounter) {
            this.winCounter = winCounter;
            return this;
        }

        public PlayerBuilder hand(List<Pokemon> hand) {
            this.hand = hand;
            return this;
        }

        public PlayerBuilder lock(Boolean lock) {
            this.lock = lock;
            return this;
        }

        public PlayerBuilder money(Integer money) {
            this.money = money;
            return this;
        }

        public PlayerBuilder loading(Boolean loading) {
            this.loading = loading;
            return this;
        }

        public PlayerBuilder disconnected(Boolean disconnected) {
            this.disconnected = disconnected;
            return this;
        }

        public PlayerBuilder pokemonTrainer(PokemonTrainer pokemonTrainer) {
            this.pokemonTrainer = pokemonTrainer;
            return this;
        }

        public PlayerBuilder opponentPlayer(Player opponentPlayer) {
            this.opponentPlayer = opponentPlayer;
            return this;
        }

        public Player build() {
            Player player = new Player();
            player.setId(id);
            player.setUser(user);
            player.setBoardGame(boardGame);
            player.setLevel(Objects.nonNull(level) ? level : DEFAULT_LEVEL);
            player.setExperiencePoint(Objects.nonNull(experiencePoint) ? experiencePoint : DEFAULT_EXPERIENCE_POINT);
            player.setLifePoint(Objects.nonNull(lifePoint) ? lifePoint : DEFAULT_LIFE_POINT);
            player.setWinCounter(Objects.nonNull(winCounter) ? winCounter : DEFAULT_WIN_COUNTER);
            player.setHand(hand);
            player.setLock(Objects.nonNull(lock) ? lock : DEFAULT_LOCK_HAND);
            player.setMoney(Objects.nonNull(money) ? money : DEFAULT_MONEY);
            player.setLoading(loading);
            player.setDisconnected(disconnected);
            player.setPokemonTrainer(pokemonTrainer);
            player.setOpponentPlayer(opponentPlayer);
            return player;
        }
    }

    @Nullable
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NonNull
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        GenericValidator.notNull(user, "user");
        this.user = user;
    }

    @NonNull
    public BoardGame getBoardGame() {
        return boardGame;
    }

    public void setBoardGame(BoardGame boardGame) {
        GenericValidator.notNull(boardGame, "boardGame");
        this.boardGame = boardGame;
    }

    @NonNull
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        GenericValidator.notNull(level, "level");
        this.level = level;
    }

    @NonNull
    public Integer getExperiencePoint() {
        return experiencePoint;
    }

    public void setExperiencePoint(Integer experiencePoint) {
        GenericValidator.notNull(experiencePoint, "experiencePoint");
        this.experiencePoint = experiencePoint;
    }

    @NonNull
    public Integer getLifePoint() {
        return lifePoint;
    }

    public void setLifePoint(Integer lifePoint) {
        GenericValidator.notNull(lifePoint, "lifePoint");
        this.lifePoint = lifePoint;
    }

    @NonNull
    public Integer getWinCounter() {
        return winCounter;
    }

    public void setWinCounter(Integer winCounter) {
        GenericValidator.notNull(winCounter, "winCounter");
        this.winCounter = winCounter;
    }

    @NonNull
    public List<Pokemon> getHand() {
        return hand;
    }

    public void setHand(List<Pokemon> hand) {
        if (Objects.isNull(hand)) {
            hand = new ArrayList<>();
        }
        this.hand = hand;
    }

    @NotNull
    public Boolean isLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        GenericValidator.notNull(lock, "lock");
        this.lock = lock;
    }

    @NonNull
    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        GenericValidator.notNull(money, "money");
        this.money = money;
    }

    @NotNull
    public Boolean isLoading() {
        return loading;
    }

    public void setLoading(Boolean loading) {
        GenericValidator.notNull(loading, "loading");
        this.loading = loading;
    }

    @NotNull
    public Boolean isDisconnected() {
        return disconnected;
    }

    public void setDisconnected(Boolean disconnected) {
        GenericValidator.notNull(disconnected, "disconnected");
        this.disconnected = disconnected;
    }

    @Nullable
    public PokemonTrainer getPokemonTrainer() {
        return pokemonTrainer;
    }

    public void setPokemonTrainer(PokemonTrainer pokemonTrainer) {
        this.pokemonTrainer = pokemonTrainer;
    }

    @Nullable
    public Player getOpponentPlayer() {
        return opponentPlayer;
    }

    public void setOpponentPlayer(Player opponentPlayer) {
        this.opponentPlayer = opponentPlayer;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Player && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "Player [id=%s, user=%s, boardGame=%s, level=%s, experiencePoint=%s, lifePoint=%s, winCounter=%s, hand=%s, money=%s, loading=%s, disconnected=%s, pokemonTrainer=%s, opponentPlayer=%s]", this.id, this.user, this.boardGame, this.level, this.experiencePoint, this.lifePoint, this.winCounter, this.hand, this.money, this.loading, this.disconnected, this.pokemonTrainer, this.opponentPlayer);
    }
}
