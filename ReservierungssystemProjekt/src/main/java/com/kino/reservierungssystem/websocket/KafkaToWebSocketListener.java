package com.kino.reservierungssystem.websocket;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KafkaToWebSocketListener {

    private final SimpMessagingTemplate messagingTemplate;

    public KafkaToWebSocketListener(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topics = "kino.response", groupId = "kino-group")
    public void handleKafkaResponse(Map<String, Object> response) {
        String requestId = (String) response.get("requestId");
        Object data = response.get("data");

        // Nachricht über WebSocket an das Frontend senden
        messagingTemplate.convertAndSend("/topic/kafkaResponse", response);
    }
}
