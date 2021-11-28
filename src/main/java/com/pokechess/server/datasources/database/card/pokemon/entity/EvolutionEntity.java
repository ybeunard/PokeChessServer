package com.pokechess.server.datasources.database.card.pokemon.entity;

import com.pokechess.server.datasources.database.card.entity.ConditionEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.List;

@Entity(name = "evolution_entity")
public class EvolutionEntity {
    private Integer id;
    private PokemonEntity pokemon;
    private String description;
    private List<ConditionEntity> conditions;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinColumn(name = "pokemon_evolved_id", referencedColumnName = "pokemon_id", nullable = false)
    public PokemonEntity getPokemon() {
        return pokemon;
    }

    public void setPokemon(PokemonEntity pokemon) {
        this.pokemon = pokemon;
    }

    @Column(nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinColumn(name = "evolution_id", referencedColumnName = "id", nullable = false)
    public List<ConditionEntity> getConditions() {
        return conditions;
    }

    public void setConditions(List<ConditionEntity> conditions) {
        this.conditions = conditions;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof EvolutionEntity && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "EvolutionEntity [id=%s, pokemon=%s, description=%s, conditions=%s]", this.id, this.pokemon, this.description, this.conditions);
    }
}
