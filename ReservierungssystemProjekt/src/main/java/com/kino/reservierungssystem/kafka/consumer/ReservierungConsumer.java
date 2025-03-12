package com.kino.reservierungssystem.kafka.consumer;

import com.kino.reservierungssystem.dto.ReservierungDTO;
import com.kino.reservierungssystem.kafka.request.KafkaRequestSender;
import com.kino.reservierungssystem.kafka.response.KafkaResponseHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class ReservierungConsumer {

    private final KafkaRequestSender kafkaRequestSender;
    private final KafkaResponseHandler kafkaResponseHandler;

    public ReservierungConsumer(KafkaRequestSender kafkaRequestSender, KafkaResponseHandler kafkaResponseHandler) {
        this.kafkaRequestSender = kafkaRequestSender;
        this.kafkaResponseHandler = kafkaResponseHandler;
    }

    @KafkaListener(topics = "reservierung.getById", groupId = "kino-group")
    public void handleGetReservierungById(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        ReservierungDTO reservierungDTO = (ReservierungDTO) request.get("data");

        kafkaResponseHandler.sendResponse(requestId, reservierungDTO);
    }

    @KafkaListener(topics = "reservierung.getAll", groupId = "kino-group")
    public void handleGetAllReservierungen(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        kafkaResponseHandler.sendResponse(requestId, request.get("data"));
    }

    @KafkaListener(topics = "reservierung.convert", groupId = "kino-group")
    public void handleReservierungUmwandlung(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        Long reservierungsId = ((Number) request.get("reservierungsId")).longValue();

        // Anfrage an die Datenbank weiterleiten
        kafkaRequestSender.sendRequest("db.reservierung.convert", reservierungsId, Object.class)
                .thenAccept(updatedReservierung -> kafkaResponseHandler.sendResponse(requestId, updatedReservierung));
    }

}
