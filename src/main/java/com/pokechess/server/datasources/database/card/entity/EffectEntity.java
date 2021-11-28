package com.pokechess.server.datasources.database.card.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.List;

@Entity(name = "effect_entity")
public class EffectEntity {
    private Integer id;
    private String applyWhen;
    private List<ConditionEntity> conditions;
    private String duration;
    private EffectEntity effect;
    private String effectName;
    private List<EffectEntity> effects;
    private String name;
    private Integer maxCumulate;
    private Integer percentage;
    private Integer power;
    private String status;
    private String targets;
    private String triggerWhen;
    private String type;
    private Integer value;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "apply_when")
    public String getApplyWhen() {
        return applyWhen;
    }

    public void setApplyWhen(String applyWhen) {
        this.applyWhen = applyWhen;
    }

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinColumn(name="effect_id", referencedColumnName="id")
    public List<ConditionEntity> getConditions() {
        return conditions;
    }

    public void setConditions(List<ConditionEntity> conditions) {
        this.conditions = conditions;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinColumn(name = "effect_id", referencedColumnName = "id")
    public EffectEntity getEffect() {
        return effect;
    }

    public void setEffect(EffectEntity effect) {
        this.effect = effect;
    }

    @Column(name = "effect_name", nullable = false)
    public String getEffectName() {
        return effectName;
    }

    public void setEffectName(String effectName) {
        this.effectName = effectName;
    }

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinColumn(name = "effects_id", referencedColumnName = "id")
    public List<EffectEntity> getEffects() {
        return effects;
    }

    public void setEffects(List<EffectEntity> effects) {
        this.effects = effects;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "max_cumulate")
    public Integer getMaxCumulate() {
        return maxCumulate;
    }

    public void setMaxCumulate(Integer maxCumulate) {
        this.maxCumulate = maxCumulate;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTargets() {
        return targets;
    }

    public void setTargets(String targets) {
        this.targets = targets;
    }

    @Column(name = "trigger_when")
    public String getTriggerWhen() {
        return triggerWhen;
    }

    public void setTriggerWhen(String triggerWhen) {
        this.triggerWhen = triggerWhen;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof EffectEntity && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "EffectEntity [id=%s, applyWhen=%s, conditions=%s, duration=%s, effect=%s, effectName=%s, effects=%s, name=%s, maxCumulate=%s, percentage=%s, power=%s, status=%s, targets=%s, triggerWhen=%s, type=%s, value=%s]", this.id, this.applyWhen, this.conditions, this.duration, this.effect, this.effectName, this.effects, this.name, this.maxCumulate, this.percentage, this.power, this.status, this.targets, this.triggerWhen, this.type, this.value);
    }
}
