package com.pokechess.server.datasources.database.player.entity;

import com.pokechess.server.datasources.database.board.game.entity.BoardGameEntity;
import com.pokechess.server.datasources.database.card.pokemon.entity.PokemonEntity;
import com.pokechess.server.datasources.database.user.entity.UserEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.List;

@Entity(name = "player_entity")
public class PlayerEntity {
    private Integer id;
    private UserEntity user;
    private BoardGameEntity boardGame;
    private Integer level;
    private Integer experiencePoint;
    private Integer lifePoint;
    private Integer winCounter;
    private List<PokemonEntity> hand;
    private Boolean lock;
    private Integer money;
    private Boolean loading;
    private Boolean disconnected;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_user", referencedColumnName = "id", unique = true, nullable = false)
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "board_game", referencedColumnName = "id", unique = true, nullable = false)
    public BoardGameEntity getBoardGame() {
        return boardGame;
    }

    public void setBoardGame(BoardGameEntity boardGame) {
        this.boardGame = boardGame;
    }

    @Column(nullable = false)
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Column(name = "experience_point", nullable = false)
    public Integer getExperiencePoint() {
        return experiencePoint;
    }

    public void setExperiencePoint(Integer experiencePoint) {
        this.experiencePoint = experiencePoint;
    }

    @Column(name = "life_point", nullable = false)
    public Integer getLifePoint() {
        return lifePoint;
    }

    public void setLifePoint(Integer lifePoint) {
        this.lifePoint = lifePoint;
    }

    @Column(name = "win_counter", nullable = false)
    public Integer getWinCounter() {
        return winCounter;
    }

    public void setWinCounter(Integer winCounter) {
        this.winCounter = winCounter;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "player_hand_entity",
        joinColumns = @JoinColumn(name = "player_id", referencedColumnName = "id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "pokemon_id", referencedColumnName = "pokemon_id", nullable = false))
    public List<PokemonEntity> getHand() {
        return hand;
    }

    public void setHand(List<PokemonEntity> hand) {
        this.hand = hand;
    }

    @Column(nullable = false)
    public Boolean isLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }

    @Column(nullable = false)
    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Boolean isLoading() {
        return loading;
    }

    public void setLoading(Boolean loading) {
        this.loading = loading;
    }

    public Boolean isDisconnected() {
        return disconnected;
    }

    public void setDisconnected(Boolean disconnected) {
        this.disconnected = disconnected;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PlayerEntity && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "PlayerEntity [id=%s, user=%s, boardGame=%s, level=%s, experiencePoint=%s, lifePoint=%s, winCounter=%s, hand=%s, lock=%s, money=%s, loading=%s, disconnected=%s]", this.id, this.user, this.boardGame, this.level, this.experiencePoint, this.lifePoint, this.winCounter, this.hand, this.lock, this.money, this.loading, this.disconnected);
    }
}
