package com.pokechess.server.config.websocket;

import org.springframework.lang.NonNull;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;

import java.util.Optional;

public class WebSocketMessageHandler extends WebSocketHandlerDecorator {
    private static final String NULL_CHAR = "\u0000";
    private static final String END_OF_LINE = "\r\n";
    private static final String DOUBLE_LINE_BREAK = "\n\n";
    private static final String LINE_BREAK = "\n";

    public WebSocketMessageHandler(WebSocketHandler webSocketHandler) {
        super(webSocketHandler);
    }

    @Override
    public void handleMessage(@NonNull WebSocketSession session, @NonNull WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, updateBody(message));
    }

    private WebSocketMessage<?> updateBody(WebSocketMessage<?> message) {
        if (!(message instanceof TextMessage) || ((TextMessage) message).getPayload().endsWith(NULL_CHAR)) {
            return message;
        }

        String payload = ((TextMessage) message).getPayload();

        final Optional<StompCommand> stompCommand = getStompCommand(payload);

        if (stompCommand.isEmpty()) {
            return message;
        }

        if (!stompCommand.get().isBodyAllowed() && !payload.endsWith(DOUBLE_LINE_BREAK)) {
            if (payload.endsWith(LINE_BREAK)) {
                payload += LINE_BREAK;
            } else {
                payload += DOUBLE_LINE_BREAK;
            }
        }

        payload += NULL_CHAR;

        return new TextMessage(payload);
    }

    /**
     * Returns the {@link StompCommand STOMP command} associated to the specified payload.
     */
    private Optional<StompCommand> getStompCommand(String payload) {
        final int firstCarriageReturn = payload.indexOf(END_OF_LINE);

        if (firstCarriageReturn < 0) {
            return Optional.empty();
        }

        try {
            return Optional.of(
                    StompCommand.valueOf(payload.substring(0, firstCarriageReturn))
            );
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}