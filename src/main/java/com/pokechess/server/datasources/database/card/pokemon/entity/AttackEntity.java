package com.pokechess.server.datasources.database.card.pokemon.entity;

import com.pokechess.server.datasources.database.card.entity.EffectEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.List;

@Entity(name = "attack_entity")
public class AttackEntity {
    private Integer id;
    private String name;
    private String description;
    private String type;
    private String targets;
    private Integer power;
    private Integer precision;
    private Integer priority;
    private List<EffectEntity> effects;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(nullable = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(nullable = false)
    public String getTargets() {
        return targets;
    }

    public void setTargets(String targets) {
        this.targets = targets;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    @Column(nullable = false)
    public Integer getPrecision() {
        return precision;
    }

    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    @Column(nullable = false)
    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinColumn(name="attack_id", referencedColumnName="id")
    public List<EffectEntity> getEffects() {
        return effects;
    }

    public void setEffects(List<EffectEntity> effects) {
        this.effects = effects;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof AttackEntity && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "AttackEntity [id=%s, name=%s, description=%s, type=%s, targets=%s, power=%s, precision=%s, priority=%s, effects=%s]", this.id, this.name, this.description, this.type, this.targets, this.power, this.precision, this.priority, this.effects);
    }
}
