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

    @KafkaListener(topics = "auffuehrungGetAll", groupId = "kino-group")
    public void handleAuffuehrungAnfrage(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        List<AuffuehrungDTO> auffuehrungen = kafkaRequestSender.sendRequest(
                "dbAuffuehrungGetAll", null, List.class).join();
        kafkaResponseHandler.sendResponse(requestId, auffuehrungen);
    }

    @KafkaListener(topics = "auffuehrungGetById", groupId = "kino-group")
    public void handleAuffuehrungByIdAnfrage(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        Long id = ((Number) request.get("data")).longValue();

        AuffuehrungDTO auffuehrung = kafkaRequestSender.sendRequest(
                "dbAuffuehrungGetById", id, AuffuehrungDTO.class).join();
        kafkaResponseHandler.sendResponse(requestId, auffuehrung);
    }

    @KafkaListener(topics = "auffuehrungCreate", groupId = "kino-group")
    public void handleAuffuehrungErstellt(Map<String, Object> request) {
        AuffuehrungDTO auffuehrungDTO = (AuffuehrungDTO) request.get("data");
        kafkaRequestSender.sendRequest("dbAuffuehrungCreate", auffuehrungDTO, Void.class);
    }

    @KafkaListener(topics = "auffuehrungUpdate", groupId = "kino-group")
    public void handleAuffuehrungAktualisiert(Map<String, Object> request) {
        AuffuehrungDTO auffuehrungDTO = (AuffuehrungDTO) request.get("data");
        kafkaRequestSender.sendRequest("dbAuffuehrungUpdate", auffuehrungDTO, Void.class);
    }

    @KafkaListener(topics = "auffuehrungDelete", groupId = "kino-group")
    public void handleAuffuehrungGeloescht(Map<String, Object> request) {
        Long id = ((Number) request.get("data")).longValue();
        kafkaRequestSender.sendRequest("dbAuffuehrungDelete", id, Void.class);
    }
}
