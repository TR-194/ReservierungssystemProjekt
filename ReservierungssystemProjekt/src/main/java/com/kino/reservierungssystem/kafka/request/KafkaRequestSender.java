package com.kino.reservierungssystem.kafka.request;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class KafkaRequestSender {

    private final KafkaTemplate<String, Map<String, Object>> kafkaTemplate;
    private final Map<String, CompletableFuture<Object>> responseFutures = new ConcurrentHashMap<>();

    public KafkaRequestSender(KafkaTemplate<String, Map<String, Object>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public <T> CompletableFuture<T> sendRequest(String topic, Object data, Class<T> responseType) {
        String requestId = UUID.randomUUID().toString();
        CompletableFuture<T> future = new CompletableFuture<>();
        responseFutures.put(requestId, (CompletableFuture<Object>) future);  // Store future for later completion

        Map<String, Object> message = Map.of("requestId", requestId, "data", data);
        kafkaTemplate.send(topic, message);

        return future;
    }

    /**
     * Completes the pending future with the received Kafka response.
     */
    public void completeResponse(String requestId, Object data) {
        CompletableFuture<Object> future = responseFutures.remove(requestId);
        if (future != null) {
            future.complete(data);
        }
    }
}
