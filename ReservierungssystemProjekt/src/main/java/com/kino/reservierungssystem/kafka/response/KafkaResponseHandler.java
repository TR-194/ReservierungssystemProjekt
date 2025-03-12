package com.kino.reservierungssystem.kafka.response;

import com.kino.reservierungssystem.kafka.request.KafkaRequestSender;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class KafkaResponseHandler {

    private final KafkaRequestSender kafkaRequestSender;

    private final Map<String, CompletableFuture<Object>> responseFutures = new ConcurrentHashMap<>();

    public KafkaResponseHandler(KafkaRequestSender kafkaRequestSender) {
        this.kafkaRequestSender = kafkaRequestSender;
    }

    /**
     * Listens for responses from Kafka and forwards them to the appropriate request.
     */
    @KafkaListener(topics = "kino.response", groupId = "kino-group")
    public void handleResponse(Map<String, Object> response) {
        String requestId = (String) response.get("requestId");
        Object data = response.get("data");

        CompletableFuture<Object> future = responseFutures.remove(requestId);
        if (future != null) {
            future.complete(data);
        }
    }
    /**
     * Sends a response message back to Kafka.
     */
    public void sendResponse(String requestId, Object data) {
        // This should send the response to Kafka so the requester gets it
        // KafkaTemplate is not injected here; sending should be done in a Producer class
    }
}
