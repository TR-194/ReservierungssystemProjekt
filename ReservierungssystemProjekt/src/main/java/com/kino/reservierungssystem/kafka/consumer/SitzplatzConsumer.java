package com.kino.reservierungssystem.kafka.consumer;

import com.kino.reservierungssystem.dto.SitzplatzDTO;
import com.kino.reservierungssystem.kafka.response.KafkaResponseHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class SitzplatzConsumer {

    private final KafkaResponseHandler kafkaResponseHandler;

    public SitzplatzConsumer(KafkaResponseHandler kafkaResponseHandler) {
        this.kafkaResponseHandler = kafkaResponseHandler;
    }

    @KafkaListener(topics = "sitzplatz.getAll", groupId = "kino-group")
    public void handleGetAllSitzplaetze(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        kafkaResponseHandler.sendResponse(requestId, request.get("data"));
    }

    @KafkaListener(topics = "sitzplatz.getById", groupId = "kino-group")
    public void handleGetSitzplatzById(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        kafkaResponseHandler.sendResponse(requestId, request.get("data"));
    }

    @KafkaListener(topics = "sitzplatz.updateStatus", groupId = "kino-group")
    public void handleUpdateSitzplatzStatus(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        kafkaResponseHandler.sendResponse(requestId, request.get("data"));
    }
}
