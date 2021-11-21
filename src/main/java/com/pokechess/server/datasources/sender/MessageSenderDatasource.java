package com.pokechess.server.datasources.sender;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageSenderDatasource {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public MessageSenderDatasource(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void sendMessage(String destination, Object payload) {
        this.simpMessagingTemplate.convertAndSend(destination, payload);
    }
}
