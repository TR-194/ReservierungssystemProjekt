package com.kino.reservierungssystem.kafka.consumer;

import com.kino.reservierungssystem.dto.BuchungDTO;
import com.kino.reservierungssystem.kafka.request.KafkaRequestSender;
import com.kino.reservierungssystem.kafka.response.KafkaResponseHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class BuchungConsumer {

    private final KafkaRequestSender kafkaRequestSender;
    private final KafkaResponseHandler kafkaResponseHandler;

    public BuchungConsumer(KafkaRequestSender kafkaRequestSender, KafkaResponseHandler kafkaResponseHandler) {
        this.kafkaRequestSender = kafkaRequestSender;
        this.kafkaResponseHandler = kafkaResponseHandler;
    }

    @KafkaListener(topics = "buchung.getAll", groupId = "kino-group")
    public void handleBuchungAnfrage(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        List<BuchungDTO> buchungen = kafkaRequestSender.sendRequest(
                "db.buchung.getAll", null, List.class).join();
        kafkaResponseHandler.sendResponse(requestId, buchungen);
    }

    @KafkaListener(topics = "buchung.getById", groupId = "kino-group")
    public void handleBuchungByIdAnfrage(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        Long id = ((Number) request.get("data")).longValue();

        BuchungDTO buchung = kafkaRequestSender.sendRequest(
                "db.buchung.getById", id, BuchungDTO.class).join();
        kafkaResponseHandler.sendResponse(requestId, buchung);
    }

    @KafkaListener(topics = "buchung.create", groupId = "kino-group")
    public void handleBuchungErstellt(Map<String, Object> request) {
        BuchungDTO buchungDTO = (BuchungDTO) request.get("data");
        kafkaRequestSender.sendRequest("db.buchung.create", buchungDTO, Void.class);
    }

    @KafkaListener(topics = "buchung.delete", groupId = "kino-group")
    public void handleBuchungGeloescht(Map<String, Object> request) {
        Long id = ((Number) request.get("data")).longValue();
        kafkaRequestSender.sendRequest("db.buchung.delete", id, Void.class);
    }
}
