package com.pokechess.server.services.security;

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

import static com.pokechess.server.config.websocket.WebSocketConfig.SIMPLE_BROKER_PARTY;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebsocketSubscriptionRegistryService extends DefaultSubscriptionRegistry implements BeanPostProcessor {
    private static final String EMPTY_MESSAGE = "";

    // Destinations
    public static final String PARTY_BROKER_DESTINATION = SIMPLE_BROKER_PARTY + "/list";

    private final ConcurrentHashMap<String, String> sessions;
    private final PlayerRepository playerRepository;

    public WebsocketSubscriptionRegistryService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
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
        if (PARTY_BROKER_DESTINATION.equals(destination)) {
            return !this.playerRepository.existsPlayerByUsername(username);
        }
        return true;
    }

    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        if (bean instanceof SimpleBrokerMessageHandler) {
            ((SimpleBrokerMessageHandler) bean).setSubscriptionRegistry(this);
        }
        return bean;
    }
}
