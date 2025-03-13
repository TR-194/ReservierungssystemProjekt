package com.kino.reservierungssystem.kafka.consumer;

import com.kino.reservierungssystem.dto.ZahlungDTO;
import com.kino.reservierungssystem.kafka.response.KafkaResponseHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class ZahlungConsumer {

    private final KafkaResponseHandler kafkaResponseHandler;

    public ZahlungConsumer(KafkaResponseHandler kafkaResponseHandler) {
        this.kafkaResponseHandler = kafkaResponseHandler;
    }

    @KafkaListener(topics = "zahlungCreate", groupId = "kino-group")
    public void handleZahlungErstellung(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        kafkaResponseHandler.sendResponse(requestId, request.get("data"));
    }

    @KafkaListener(topics = "zahlungGetByBuchungId", groupId = "kino-group")
    public void handleZahlungByBuchungId(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        kafkaResponseHandler.sendResponse(requestId, request.get("data"));
    }
}
