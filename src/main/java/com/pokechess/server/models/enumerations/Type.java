package com.pokechess.server.models.enumerations;

import org.springframework.lang.Nullable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Type {
    NO_TYPE(null), STEEL("metal"), FIGHTING("combat"), DRAGON("dragon"), WATER("eau"),
    ELECTRIC("electrique"), FAIRY("fee"), FIRE("feu"), ICE("glace"), BUG("insecte"), NORMAL("normal"),
    GRASS("plante"), POISON("poison"), GROUND("sol"), FLYING("vol"), PSYCHIC("psy"), ROCK("pierre"),
    GHOST("fantome"), DARK("tenebre");

    private final String typeName;

    Type(String typeName) {
        this.typeName = typeName;
    }

    @Nullable
    public String getTypeName() {
        return typeName;
    }

    @Nullable
    public static Type getEnum(String name) {
        for (Type e : values()) {
            if (e.name().equals(name)) {
                return e;
            }
        }
        return null;
    }

    private static final Map<Type, List<Integer>> resistanceMap;
    private static final List<Type> typeList = Arrays.asList(STEEL, FIGHTING, DRAGON, WATER, ELECTRIC, FAIRY, FIRE, ICE, BUG,
            NORMAL, GRASS, POISON, GROUND, FLYING, PSYCHIC, ROCK, GHOST, DARK);

    public static Double getMultiplicative(Type type1, Type type2, Type attackType) {
        int indexAttackType = typeList.indexOf(attackType);
        int resistance = resistanceMap.get(type1).get(indexAttackType) + resistanceMap.get(NO_TYPE.equals(type2) ? type1 : type2).get(indexAttackType);
        if (resistance < 0) {
            return 0.5;
        } else if (resistance == 0) {
            return 1.0;
        } else {
            return 2.0;
        }
    }

    public static List<Type> getWeaknesses(Type type1, Type type2) {
        List<Integer> type1Resistance = resistanceMap.get(type1);
        List<Integer> type2Resistance = resistanceMap.get(NO_TYPE.equals(type2) ? type1 : type2);
        List<Type> weaknesses = new ArrayList<>();
        for (int index = 0; index < type1Resistance.size(); index++) {
            int resistance = type1Resistance.get(index) + type2Resistance.get(index);
            if (resistance > 0) {
                weaknesses.add(typeList.get(index));
            }
        }
        return weaknesses;
    }

    public static List<Type> getResistances(Type type1, Type type2) {
        List<Integer> type1Resistance = resistanceMap.get(type1);
        List<Integer> type2Resistance = resistanceMap.get(NO_TYPE.equals(type2) ? type1 : type2);
        List<Type> resistances = new ArrayList<>();
        for (int index = 0; index < type1Resistance.size(); index++) {
            int resistance = type1Resistance.get(index) + type2Resistance.get(index);
            if (resistance < 0) {
                resistances.add(typeList.get(index));
            }
        }
        return resistances;
    }

    static {
        resistanceMap = Stream.of(
                new AbstractMap.SimpleEntry<>(STEEL, Arrays.asList(-1, 1, -1, 0, 0, -1, 1, -1, -1, -1, -1, -1, 1, -1, -1, -1, 0, 0)),
                new AbstractMap.SimpleEntry<>(FIGHTING, Arrays.asList(0, 0, 0, 0, 0, 1, 0, 0, -1, 0, 0, 0, 0, 1, 1, -1, 0, -1)),
                new AbstractMap.SimpleEntry<>(DRAGON, Arrays.asList(0, 0, 1, -1, -1, 1, -1, 1, 0, 0, -1, 0, 1, -1, 0, 0, 0, 0)),
                new AbstractMap.SimpleEntry<>(WATER, Arrays.asList(-1, 0, 0, -1, 1, 0, -1, -1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0)),
                new AbstractMap.SimpleEntry<>(ELECTRIC, Arrays.asList(-1, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 1, -1, 0, 0, 0, 0)),
                new AbstractMap.SimpleEntry<>(FAIRY, Arrays.asList(1, -1, -1, 0, 0, 0, 0, 0, -1, 0, 0, 1, 0, 0, 0, 0, 0, -1)),
                new AbstractMap.SimpleEntry<>(FIRE, Arrays.asList(-1, 0, 0, 1, 0, -1, -1, -1, -1, 0, -1, 0, 1, 0, 0, 1, 0, 0)),
                new AbstractMap.SimpleEntry<>(ICE, Arrays.asList(1, 1, 0, 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0)),
                new AbstractMap.SimpleEntry<>(BUG, Arrays.asList(0, -1, 0, 0, 0, 0, 1, 0, 0, 0, -1, 0, -1, 1, 0, 1, 0, 0)),
                new AbstractMap.SimpleEntry<>(NORMAL, Arrays.asList(0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0)),
                new AbstractMap.SimpleEntry<>(GRASS, Arrays.asList(0, 0, 0, -1, -1, 0, 1, 1, 1, 0, -1, 1, -1, 1, 0, 0, 0, 0)),
                new AbstractMap.SimpleEntry<>(POISON, Arrays.asList(0, -1, 0, 0, 0, -1, 0, 0, -1, 0, -1, -1, 1, 0, 1, 0, 0, 0)),
                new AbstractMap.SimpleEntry<>(GROUND, Arrays.asList(0, 0, 0, 1, -1, 0, 0, 1, 0, 0, 1, -1, 0, 0, 0, -1, 0, 0)),
                new AbstractMap.SimpleEntry<>(FLYING, Arrays.asList(0, -1, 0, 0, 1, 0, 0, 1, -1, 0, -1, 0, -1, 0, 0, 1, 0, 0)),
                new AbstractMap.SimpleEntry<>(PSYCHIC, Arrays.asList(0, -1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, -1, 0, 1, 1)),
                new AbstractMap.SimpleEntry<>(ROCK, Arrays.asList(1, 1, 0, 1, 0, 0, -1, 0, 0, -1, 1, -1, 1, -1, 0, 0, 0, 0)),
                new AbstractMap.SimpleEntry<>(GHOST, Arrays.asList(0, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, -1, 0, 0, 0, 0, 1, 1)),
                new AbstractMap.SimpleEntry<>(DARK, Arrays.asList(0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, -1, 0, -1, -1))
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
