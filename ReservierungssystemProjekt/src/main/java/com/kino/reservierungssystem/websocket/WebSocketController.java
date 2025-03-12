package com.kino.reservierungssystem.websocket;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class WebSocketController {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public WebSocketController(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @MessageMapping("/kafkaRequest")
    public void sendKafkaRequest(Map<String, Object> message) {
        String eventType = (String) message.get("eventType");

        System.out.println("Empfangene Anfrage über WebSocket: " + eventType);
        kafkaTemplate.send(eventType, message);
    }
}