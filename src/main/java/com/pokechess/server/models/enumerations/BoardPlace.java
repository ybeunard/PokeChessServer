package com.pokechess.server.models.enumerations;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Objects;

public enum BoardPlace {
    BENCH("bench"), BENCH_OVERLOAD("overload"), CENTER("center"),
    OFFENSIVE("offensive"), DEFENSIVE("defensive");

    public static final List<BoardPlace> BOARD_PLACES = List.of(OFFENSIVE, DEFENSIVE);
    public static final List<BoardPlace> EXTERN_PLACES = List.of(BENCH_OVERLOAD, CENTER);

    private final String placeName;

    BoardPlace(String placeName) {
        this.placeName = placeName;
    }

    @NonNull
    public String getPlaceName() {
        return placeName;
    }

    @Nullable
    public static BoardPlace getEnum(String name) {
        for (BoardPlace e : values()) {
            if (e.name().equals(name) || e.getPlaceName().equals(name)) {
                return e;
            }
        }
        return null;
    }

    public boolean equalsWithBenchEquality(BoardPlace place) {
        if (Objects.isNull(place)) return false;
        if (this.equals(place)) return true;
        if (OFFENSIVE.equals(this) && DEFENSIVE.equals(place)) return true;
        if (DEFENSIVE.equals(this) && OFFENSIVE.equals(place)) return true;
        if (BENCH.equals(this) && BENCH_OVERLOAD.equals(place)) return true;
        return BENCH_OVERLOAD.equals(this) && BENCH.equals(place);
    }
}
