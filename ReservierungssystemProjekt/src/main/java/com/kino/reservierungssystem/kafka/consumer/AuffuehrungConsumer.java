package com.kino.reservierungssystem.kafka.consumer;

import com.kino.reservierungssystem.dto.AuffuehrungDTO;
import com.kino.reservierungssystem.kafka.request.KafkaRequestSender;
import com.kino.reservierungssystem.kafka.response.KafkaResponseHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class AuffuehrungConsumer {

    private final KafkaRequestSender kafkaRequestSender;
    private final KafkaResponseHandler kafkaResponseHandler;

    public AuffuehrungConsumer(KafkaRequestSender kafkaRequestSender, KafkaResponseHandler kafkaResponseHandler) {
        this.kafkaRequestSender = kafkaRequestSender;
        this.kafkaResponseHandler = kafkaResponseHandler;
    }

    @KafkaListener(topics = "kino.auffuehrung.getAll", groupId = "kino-group")
    public void handleAuffuehrungAnfrage(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        List<AuffuehrungDTO> auffuehrungen = kafkaRequestSender.sendRequest(
                "db.auffuehrung.getAll", null, List.class).join();
        kafkaResponseHandler.sendResponse(requestId, auffuehrungen);
    }

    @KafkaListener(topics = "kino.auffuehrung.getById", groupId = "kino-group")
    public void handleAuffuehrungByIdAnfrage(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        Long id = ((Number) request.get("data")).longValue();

        AuffuehrungDTO auffuehrung = kafkaRequestSender.sendRequest(
                "db.auffuehrung.getById", id, AuffuehrungDTO.class).join();
        kafkaResponseHandler.sendResponse(requestId, auffuehrung);
    }

    @KafkaListener(topics = "kino.auffuehrung.create", groupId = "kino-group")
    public void handleAuffuehrungErstellt(Map<String, Object> request) {
        AuffuehrungDTO auffuehrungDTO = (AuffuehrungDTO) request.get("data");
        kafkaRequestSender.sendRequest("db.auffuehrung.create", auffuehrungDTO, Void.class);
    }

    @KafkaListener(topics = "kino.auffuehrung.update", groupId = "kino-group")
    public void handleAuffuehrungAktualisiert(Map<String, Object> request) {
        AuffuehrungDTO auffuehrungDTO = (AuffuehrungDTO) request.get("data");
        kafkaRequestSender.sendRequest("db.auffuehrung.update", auffuehrungDTO, Void.class);
    }

    @KafkaListener(topics = "kino.auffuehrung.delete", groupId = "kino-group")
    public void handleAuffuehrungGeloescht(Map<String, Object> request) {
        Long id = ((Number) request.get("data")).longValue();
        kafkaRequestSender.sendRequest("db.auffuehrung.delete", id, Void.class);
    }
}
