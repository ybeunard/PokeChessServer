package com.pokechess.server.datasources.database.party.entity;

import com.pokechess.server.datasources.database.player.entity.PlayerEntity;
import com.pokechess.server.datasources.database.user.entity.UserEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.List;

@Entity(name = "party_entity")
public class PartyEntity {
    private Integer id;
    private UserEntity owner;
    private String name;
    private String password;
    private List<PlayerEntity> players;
    private String state;
    private Integer currentTurnNumber;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", referencedColumnName = "id", unique = true, nullable = false)
    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    @Column(unique = true, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name="party_id", referencedColumnName="id", nullable = false)
    public List<PlayerEntity> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerEntity> players) {
        this.players = players;
    }

    @Column(nullable = false)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "current_turn_number", nullable = false)
    public Integer getCurrentTurnNumber() {
        return currentTurnNumber;
    }

    public void setCurrentTurnNumber(Integer currentTurnNumber) {
        this.currentTurnNumber = currentTurnNumber;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PartyEntity && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "PartyEntity [id=%s, owner=%s, name=%s, password=%s, players=%s, state=%s, currentTurnNumber=%s]", this.id, this.owner, this.name, this.password, this.players, this.state, this.currentTurnNumber);
    }
}
