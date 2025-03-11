package com.kino.reservierungssystem.kafka.response;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class KafkaResponseHandler {

    private final Map<String, CompletableFuture<?>> responseMap = new ConcurrentHashMap<>();

    /**
     * Erzeugt eine eindeutige Request-ID für Kafka-Anfragen.
     */
    public String generateRequestId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Holt eine einzelne Antwort aus der Kafka Response Map.
     */
    public <T> CompletableFuture<T> getSingleResponse(String requestId, Class<T> responseType) {
        CompletableFuture<T> future = new CompletableFuture<>();
        responseMap.put(requestId, future);
        return future;
    }

    /**
     * Holt eine Liste von Antworten aus der Kafka Response Map.
     */
    public <T> CompletableFuture<List<T>> getResponseList(String requestId, Class<T> responseType) {
        CompletableFuture<List<T>> future = new CompletableFuture<>();
        responseMap.put(requestId, future);
        return future;
    }

    /**
     * Kafka Listener, der auf Antworten von Kafka wartet und die jeweilige Antwort zur passenden Request-ID zuordnet.
     */
    @KafkaListener(topics = "kino.response", groupId = "kino-group")
    public void handleResponse(Map<String, Object> response) {
        String requestId = (String) response.get("requestId");
        Object data = response.get("data");

        CompletableFuture<?> future = responseMap.remove(requestId);
        if (future != null) {
            completeFuture(future, data);
        }
    }

    /**
     * Behandelt verschiedene Arten von CompletableFuture (Single-Objekt oder Liste).
     */
    private <T> void completeFuture(CompletableFuture<?> future, Object data) {
        if (future instanceof CompletableFuture<?>) {
            ((CompletableFuture<Object>) future).complete(data);
        }
    }

    /**
     * Methode zum Versenden einer Kafka-Antwort.
     */
    public void sendResponse(String requestId, Object data) {
        Map<String, Object> response = Map.of("requestId", requestId, "data", data);
        // Hier würde die Kafka Sendelogik stehen (z.B. kafkaTemplate.send("kino.response", response));
    }
}
