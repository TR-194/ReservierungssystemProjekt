package com.kino.reservierungssystem.controller;

import com.kino.reservierungssystem.dto.SitzplatzDTO;
import com.kino.reservierungssystem.kafka.request.KafkaRequestSender;
import com.kino.reservierungssystem.kafka.response.KafkaResponseHandler;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/sitzplaetze")
public class SitzplatzController {

    private final KafkaRequestSender kafkaRequestSender;
    private final KafkaResponseHandler kafkaResponseHandler;

    public SitzplatzController(KafkaRequestSender kafkaRequestSender, KafkaResponseHandler kafkaResponseHandler) {
        this.kafkaRequestSender = kafkaRequestSender;
        this.kafkaResponseHandler = kafkaResponseHandler;
    }

    /**
     * Holt alle Sitzplätze asynchron über Kafka.
     */
    @GetMapping
    public CompletableFuture<List<SitzplatzDTO>> getAllSitzplaetze() {
        String requestId = kafkaResponseHandler.generateRequestId();
        kafkaRequestSender.sendRequest("sitzplatz.getAll", requestId, null);
        return kafkaResponseHandler.getResponseList(requestId, SitzplatzDTO.class);
    }

    /**
     * Holt einen bestimmten Sitzplatz anhand der ID.
     */
    @GetMapping("/{id}")
    public CompletableFuture<SitzplatzDTO> getSitzplatzById(@PathVariable Long id) {
        String requestId = kafkaResponseHandler.generateRequestId();
        kafkaRequestSender.sendRequest("sitzplatz.getById", requestId, id);
        return kafkaResponseHandler.getSingleResponse(requestId, SitzplatzDTO.class);
    }

    /**
     * Aktualisiert den Status eines Sitzplatzes.
     */
    @PutMapping("/{id}/status")
    public void updateSitzplatzStatus(@PathVariable Long id, @RequestParam String status) {
        kafkaRequestSender.sendRequest("sitzplatz.updateStatus", null, new SitzplatzDTO(id, 0, status, null, null));
    }
}
