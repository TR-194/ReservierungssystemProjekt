package com.kino.reservierungssystem.kafka.consumer;

import com.kino.reservierungssystem.dto.StatistikDTO;
import com.kino.reservierungssystem.kafka.response.KafkaResponseHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class StatistikConsumer {

    private final KafkaResponseHandler kafkaResponseHandler;

    public StatistikConsumer(KafkaResponseHandler kafkaResponseHandler) {
        this.kafkaResponseHandler = kafkaResponseHandler;
    }

    @KafkaListener(topics = "statistik.einnahmen.auffuehrung", groupId = "kino-group")
    public void handleEinnahmenAuffuehrung(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        kafkaResponseHandler.sendResponse(requestId, request.get("data"));
    }

    @KafkaListener(topics = "statistik.einnahmen.film", groupId = "kino-group")
    public void handleEinnahmenFilm(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        kafkaResponseHandler.sendResponse(requestId, request.get("data"));
    }
}
