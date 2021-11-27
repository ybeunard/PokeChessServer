package com.pokechess.server.services.security;

import com.pokechess.server.exceptions.PartyException;
import com.pokechess.server.models.enumerations.PartyState;
import com.pokechess.server.models.party.Party;
import com.pokechess.server.models.party.Player;
import com.pokechess.server.repositories.message.MessageRepository;
import com.pokechess.server.repositories.party.PartyRepository;
import com.pokechess.server.repositories.player.PlayerRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.broker.DefaultSubscriptionRegistry;
import org.springframework.messaging.simp.broker.SimpleBrokerMessageHandler;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.pokechess.server.config.websocket.WebSocketConfig.SIMPLE_BROKER_PARTY;
import static org.springframework.messaging.simp.SimpMessageHeaderAccessor.SESSION_ID_HEADER;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SessionManagerService extends DefaultSubscriptionRegistry implements BeanPostProcessor {
    private static final String EMPTY_MESSAGE = "";

    // Destinations
    public static final String PARTY_CREATION_BROKER_DESTINATION = SIMPLE_BROKER_PARTY + "/creation";
    public static final String PARTY_UPDATE_PLAYER_NUMBER_BROKER_DESTINATION = SIMPLE_BROKER_PARTY + "/update/playernumber";
    public static final String PARTY_DELETED_BROKER_DESTINATION = SIMPLE_BROKER_PARTY + "/deleted";
    public static final String SPECIFIC_PARTY_UPDATE_PLAYER_BROKER_DESTINATION = SIMPLE_BROKER_PARTY + "/%s/update/player";
    public static final String SPECIFIC_PARTY_UPDATE_STATE_BROKER_DESTINATION = SIMPLE_BROKER_PARTY + "/%s/update/state";

    private final PlayerRepository playerRepository;
    private final PartyRepository partyRepository;
    private final MessageRepository messageRepository;

    private final MultiValueMap<String, String> sessions;

    public SessionManagerService(PlayerRepository playerRepository,
                                 PartyRepository partyRepository,
                                 MessageRepository messageRepository) {
        this.playerRepository = playerRepository;
        this.partyRepository = partyRepository;
        this.messageRepository = messageRepository;
        this.sessions = new LinkedMultiValueMap<>();
    }

    @EventListener
    public void handleSessionConnected(SessionConnectEvent event) {
        Principal user = event.getUser();
        String sessionId = (String) event.getMessage().getHeaders().get(SESSION_ID_HEADER);
        if (Objects.isNull(user) ||Objects.isNull(sessionId)) {
            return;
        }
        this.sessions.compute(user.getName(), (_username, sessionIds) -> {
            if (sessionIds == null) {
                return Collections.singletonList(sessionId);
            } else {
                List<String> result = new ArrayList<>(sessionIds.size() + 1);
                result.addAll(sessionIds);
                result.add(sessionId);
                return result;
            }
        });
    }

    public boolean isConnected(String username) {
        return this.sessions.containsKey(username);
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
        super.addSubscriptionInternal(sessionId, subscriptionId, destination, message);
    }

    public void removeSubscription(String username, String destination) {
        if (Objects.isNull(username) || Objects.isNull(destination)) {
            return;
        }

        Message<String> message = new GenericMessage<>(EMPTY_MESSAGE);
        MultiValueMap<String, String> sessionIdToSubscriptions = super.findSubscriptionsInternal(destination, message);

        this.getSessionsByUsername(username)
                .forEach(sessionId -> Optional.ofNullable(sessionIdToSubscriptions.get(sessionId))
                        .ifPresent(subscriptions -> subscriptions
                                .forEach(subscriptionId -> super.removeSubscriptionInternal(sessionId, subscriptionId, message))));
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        Principal user = event.getUser();
        String sessionId = (String) event.getMessage().getHeaders().get(SESSION_ID_HEADER);
        if (Objects.isNull(user) ||Objects.isNull(sessionId)) {
            return;
        }

        List<String> sessions = this.sessions.computeIfPresent(user.getName(), (_username, sessionIds) -> {
            if (sessionIds.size() == 1 && sessionId.equals(sessionIds.get(0))) {
                return null;
            } else {
                List<String> result = new ArrayList<>(sessionIds);
                result.remove(sessionId);
                return result.isEmpty() ? null : result;
            }
        });

        if (Objects.isNull(sessions)) {
            this.leaveParty(user.getName());
        }
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

    @NonNull
    private List<String> getSessionsByUsername(String username) {
        if (!this.sessions.containsKey(username)) {
            return Collections.emptyList();
        }
        return this.sessions.get(username);
    }

    public void leaveParty(String playerUsername) {
        try {
            Party playerParty = this.partyRepository.deletePartyByPlayerNameAndState(playerUsername, PartyState.CREATION);
            if (PartyState.DELETED.equals(playerParty.getState())) {
                this.messageRepository.sendPartyChangeStateMessage(playerParty);
                this.messageRepository.sendPartyDeletedMessage(playerParty.getName());
                playerParty.getPlayers().forEach(player -> this.deletePlayerSubscription(playerParty, player));
            } else {
                playerParty.getPlayers().removeIf(player -> {
                    this.deletePlayerSubscription(playerParty, player);
                    return player.getUser().getUsername().equals(playerUsername);
                });
                this.messageRepository.sendPartyUpdatePlayerMessage(playerParty);
                this.messageRepository.sendPartyUpdatePlayerNumberMessage(playerParty);
            }
        } catch (PartyException ignored) { }
    }

    private void deletePlayerSubscription(Party party, Player player) {
        this.removeSubscription(player.getUser().getUsername(), String.format(SPECIFIC_PARTY_UPDATE_PLAYER_BROKER_DESTINATION, party.getName()));
        this.removeSubscription(player.getUser().getUsername(), String.format(SPECIFIC_PARTY_UPDATE_STATE_BROKER_DESTINATION, party.getName()));
    }

    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        if (bean instanceof SimpleBrokerMessageHandler) {
            ((SimpleBrokerMessageHandler) bean).setSubscriptionRegistry(this);
        }
        return bean;
    }
}
