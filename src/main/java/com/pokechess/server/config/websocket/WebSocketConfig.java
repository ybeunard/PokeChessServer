package com.pokechess.server.config.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import static com.pokechess.server.filter.security.JwtRequestFilter.WEBSOCKET_CONNECTION_END_POINT;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {
    public static final String SIMPLE_BROKER_PARTY = "/parties";
    public static final String USER_DESTINATION_PREFIX = "/trainers";
    public static final String SIMPLE_ENDPOINT_GAME = "/game";

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages.anyMessage().authenticated();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(SIMPLE_BROKER_PARTY, USER_DESTINATION_PREFIX);
        config.setApplicationDestinationPrefixes(SIMPLE_ENDPOINT_GAME);
        config.setUserDestinationPrefix(USER_DESTINATION_PREFIX);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(WEBSOCKET_CONNECTION_END_POINT);
        registry.addEndpoint(WEBSOCKET_CONNECTION_END_POINT)
                .withSockJS();
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.addDecoratorFactory(WebSocketMessageHandler::new);
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
