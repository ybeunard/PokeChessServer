package com.pokechess.server.services.security;

import com.pokechess.server.models.enumerations.PartyState;
import com.pokechess.server.repositories.party.PartyRepository;
import com.pokechess.server.repositories.player.PlayerRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.broker.DefaultSubscriptionRegistry;
import org.springframework.messaging.simp.broker.SimpleBrokerMessageHandler;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.security.Principal;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.pokechess.server.config.websocket.WebSocketConfig.SIMPLE_BROKER_PARTY;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SubscriptionRegistryService extends DefaultSubscriptionRegistry implements BeanPostProcessor {
    private static final String EMPTY_MESSAGE = "";

    // Destinations
    public static final String PARTY_CREATION_BROKER_DESTINATION = SIMPLE_BROKER_PARTY + "/creation";
    public static final String PARTY_UPDATE_PLAYER_NUMBER_BROKER_DESTINATION = SIMPLE_BROKER_PARTY + "/update/playernumber";
    public static final String PARTY_DELETED_BROKER_DESTINATION = SIMPLE_BROKER_PARTY + "/deleted";
    public static final String SPECIFIC_PARTY_UPDATE_PLAYER_BROKER_DESTINATION = SIMPLE_BROKER_PARTY + "/%s/update/player";
    public static final String SPECIFIC_PARTY_UPDATE_STATE_BROKER_DESTINATION = SIMPLE_BROKER_PARTY + "/%s/update/state";

    private final ConcurrentHashMap<String, String> sessions;
    private final PlayerRepository playerRepository;
    private final PartyRepository partyRepository;

    public SubscriptionRegistryService(PlayerRepository playerRepository,
                                       PartyRepository partyRepository) {
        this.playerRepository = playerRepository;
        this.partyRepository = partyRepository;
        this.sessions = new ConcurrentHashMap<>();
    }

    @Override
    protected void addSubscriptionInternal(@NonNull String sessionId, @NonNull String subscriptionId, @NonNull String destination, Message<?> message) {
        Principal user = SimpMessageHeaderAccessor.getUser(message.getHeaders());
        if (Objects.isNull(user)) {
            return;
        }

        if (!authorizeSubscribe(user.getName(), destination)) {
            return;
        }

        this.sessions.put(sessionId, user.getName());
        super.addSubscriptionInternal(sessionId, subscriptionId, destination, message);
    }

    @Override
    public void unregisterAllSubscriptions(@NonNull String sessionId) {
        this.sessions.remove(sessionId);
        super.unregisterAllSubscriptions(sessionId);
    }

    public void removeSubscription(String username, String destination) {
        if (Objects.isNull(username) || Objects.isNull(destination)) {
            return;
        }

        Message<String> message = new GenericMessage<>(EMPTY_MESSAGE);
        MultiValueMap<String, String> sessionIdToSubscriptions = super.findSubscriptionsInternal(destination, message);

        this.sessions.entrySet().stream().filter(entry -> username.equals(entry.getValue()))
                .map(Map.Entry::getKey).forEach(sessionId -> Optional.ofNullable(sessionIdToSubscriptions
                        .get(sessionId)).ifPresent(subscriptions -> subscriptions
                                .forEach(subscriptionId -> super.removeSubscriptionInternal(sessionId, subscriptionId, message))));
    }

    private boolean authorizeSubscribe(String username, String destination) {
        switch (destination) {
            case PARTY_CREATION_BROKER_DESTINATION:
            case PARTY_UPDATE_PLAYER_NUMBER_BROKER_DESTINATION:
            case PARTY_DELETED_BROKER_DESTINATION:
                return !this.playerRepository.existsPlayerByUsername(username);
            default:
        }

        Matcher m1 = getSpecificPattern(SPECIFIC_PARTY_UPDATE_PLAYER_BROKER_DESTINATION).matcher(destination);
        if (m1.find()) {
            String partyName = m1.group(1);
            return this.partyRepository.existsByNameAndPlayerNameAndState(partyName, username, PartyState.CREATION);
        }
        Matcher m2 = getSpecificPattern(SPECIFIC_PARTY_UPDATE_STATE_BROKER_DESTINATION).matcher(destination);
        if (m2.find()) {
            String partyName = m2.group(1);
            return this.partyRepository.existsByNameAndPlayerName(partyName, username);
        }
        return true;
    }

    private Pattern getSpecificPattern(String destination) {
        return Pattern.compile(String.format("^%s$", destination.replace("/", "\\/")
                .replace("%s", "([a-zA-Z0-9_]+)")));
    }

    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        if (bean instanceof SimpleBrokerMessageHandler) {
            ((SimpleBrokerMessageHandler) bean).setSubscriptionRegistry(this);
        }
        return bean;
    }
}
