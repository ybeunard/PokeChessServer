package com.pokechess.server.models.party;

import com.pokechess.server.models.enumerations.PartyState;
import com.pokechess.server.models.globals.User;
import com.pokechess.server.models.globals.game.cards.Berry;
import com.pokechess.server.models.globals.game.cards.Pokemon;
import com.pokechess.server.models.globals.game.cards.TrainerObject;
import com.pokechess.server.models.globals.game.cards.Weather;
import com.pokechess.server.validators.GenericValidator;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.*;

public class Party {
    private static final Integer DEFAULT_CURRENT_TURN_NUMBER = 0;

    private UUID id;
    private User owner;
    private String name;
    private List<Player> players;
    private PartyState state;
    private Integer currentTurnNumber;
    private Map<Integer, List<Pokemon>> pokemonCardDraw;
    private List<TrainerObject> objectCardDraw;
    private List<Berry> berryCardDraw;
    private List<Weather> weatherCardDraw;
    private List<Pokemon> carousel;
    private List<TrainerObject> shop;
    private Weather currentWeather;

    private Party() { }

    public static PartyBuilder builder() {
        return new PartyBuilder();
    }

    public static class PartyBuilder {
        private UUID id;
        private User owner;
        private String name;
        private List<Player> players;
        private PartyState state;
        private Integer currentTurnNumber;
        private Map<Integer, List<Pokemon>> pokemonCardDraw;
        private List<TrainerObject> objectCardDraw;
        private List<Berry> berryCardDraw;
        private List<Weather> weatherCardDraw;
        private List<Pokemon> carousel;
        private List<TrainerObject> shop;
        private Weather currentWeather;

        public PartyBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public PartyBuilder owner(User owner) {
            this.owner = owner;
            return this;
        }

        public PartyBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PartyBuilder players(List<Player> players) {
            this.players = players;
            return this;
        }

        public PartyBuilder state(PartyState state) {
            this.state = state;
            return this;
        }

        public PartyBuilder currentTurnNumber(Integer currentTurnNumber) {
            this.currentTurnNumber = currentTurnNumber;
            return this;
        }

        public PartyBuilder pokemonCardDraw(Map<Integer, List<Pokemon>> pokemonCardDraw) {
            this.pokemonCardDraw = pokemonCardDraw;
            return this;
        }

        public PartyBuilder objectCardDraw(List<TrainerObject> objectCardDraw) {
            this.objectCardDraw = objectCardDraw;
            return this;
        }

        public PartyBuilder berryCardDraw(List<Berry> berryCardDraw) {
            this.berryCardDraw = berryCardDraw;
            return this;
        }

        public PartyBuilder weatherCardDraw(List<Weather> weatherCardDraw) {
            this.weatherCardDraw = weatherCardDraw;
            return this;
        }

        public PartyBuilder carousel(List<Pokemon> carousel) {
            this.carousel = carousel;
            return this;
        }

        public PartyBuilder shop(List<TrainerObject> shop) {
            this.shop = shop;
            return this;
        }

        public PartyBuilder currentWeather(Weather currentWeather) {
            this.currentWeather = currentWeather;
            return this;
        }

        public Party build() {
            Party party = new Party();
            party.setId(id);
            party.setOwner(owner);
            party.setName(name);
            party.setPlayers(players);
            party.setState(state);
            party.setCurrentTurnNumber(Objects.nonNull(currentTurnNumber) ? currentTurnNumber : DEFAULT_CURRENT_TURN_NUMBER);
            party.setPokemonCardDraw(pokemonCardDraw);
            party.setObjectCardDraw(objectCardDraw);
            party.setBerryCardDraw(berryCardDraw);
            party.setWeatherCardDraw(weatherCardDraw);
            party.setCarousel(carousel);
            party.setShop(shop);
            party.setCurrentWeather(currentWeather);
            return party;
        }
    }

    @NonNull
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        GenericValidator.notNull(id);
        this.id = id;
    }

    @NonNull
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        GenericValidator.notNull(owner);
        this.owner = owner;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        GenericValidator.notEmpty(name);
        this.name = name;
    }

    @NonNull
    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        GenericValidator.notEmpty(players);
        this.players = players;
    }

    @NonNull
    public PartyState getState() {
        return state;
    }

    public void setState(PartyState state) {
        GenericValidator.notNull(state);
        this.state = state;
    }

    @NonNull
    public Integer getCurrentTurnNumber() {
        return currentTurnNumber;
    }

    public void setCurrentTurnNumber(Integer currentTurnNumber) {
        GenericValidator.notNull(currentTurnNumber);
        this.currentTurnNumber = currentTurnNumber;
    }

    @NonNull
    public Map<Integer, List<Pokemon>> getPokemonCardDraw() {
        return pokemonCardDraw;
    }

    public void setPokemonCardDraw(Map<Integer, List<Pokemon>> pokemonCardDraw) {
        if (Objects.isNull(pokemonCardDraw)) {
            pokemonCardDraw = new HashMap<>();
        }
        this.pokemonCardDraw = pokemonCardDraw;
    }

    @NonNull
    public List<TrainerObject> getObjectCardDraw() {
        return objectCardDraw;
    }

    public void setObjectCardDraw(List<TrainerObject> objectCardDraw) {
        if (Objects.isNull(objectCardDraw)) {
            objectCardDraw = new ArrayList<>();
        }
        this.objectCardDraw = objectCardDraw;
    }

    @NonNull
    public List<Berry> getBerryCardDraw() {
        return berryCardDraw;
    }

    public void setBerryCardDraw(List<Berry> berryCardDraw) {
        if (Objects.isNull(berryCardDraw)) {
            berryCardDraw = new ArrayList<>();
        }
        this.berryCardDraw = berryCardDraw;
    }

    @NonNull
    public List<Weather> getWeatherCardDraw() {
        return weatherCardDraw;
    }

    public void setWeatherCardDraw(List<Weather> weatherCardDraw) {
        if (Objects.isNull(weatherCardDraw)) {
            weatherCardDraw = new ArrayList<>();
        }
        this.weatherCardDraw = weatherCardDraw;
    }

    @NonNull
    public List<Pokemon> getCarousel() {
        return carousel;
    }

    public void setCarousel(List<Pokemon> carousel) {
        if (Objects.isNull(carousel)) {
            carousel = new ArrayList<>();
        }
        this.carousel = carousel;
    }

    @NonNull
    public List<TrainerObject> getShop() {
        return shop;
    }

    public void setShop(List<TrainerObject> shop) {
        if (Objects.isNull(shop)) {
            shop = new ArrayList<>();
        }
        this.shop = shop;
    }

    @Nullable
    public Weather getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(Weather currentWeather) {
        this.currentWeather = currentWeather;
    }
}
