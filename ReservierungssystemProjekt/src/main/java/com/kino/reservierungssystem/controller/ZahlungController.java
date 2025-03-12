package com.kino.reservierungssystem.controller;

import com.kino.reservierungssystem.dto.ZahlungDTO;
import com.kino.reservierungssystem.kafka.request.KafkaRequestSender;
import com.kino.reservierungssystem.kafka.response.KafkaResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/zahlungen")
public class ZahlungController {

    private final KafkaRequestSender kafkaRequestSender;
    private final KafkaResponseHandler kafkaResponseHandler;

    public ZahlungController(KafkaRequestSender kafkaRequestSender, KafkaResponseHandler kafkaResponseHandler) {
        this.kafkaRequestSender = kafkaRequestSender;
        this.kafkaResponseHandler = kafkaResponseHandler;
    }

    /**
     * Erstellt eine neue Zahlung asynchron über Kafka.
     */
    @PostMapping
    public ResponseEntity<Void> createZahlung(@RequestBody ZahlungDTO zahlungDTO) {
        kafkaRequestSender.sendRequest("zahlung.create", null, zahlungDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    /**
     * Holt eine Zahlung basierend auf der Buchungs-ID.
     */
    @GetMapping("/{buchungId}")
    public CompletableFuture<ZahlungDTO> getZahlungByBuchungId(@PathVariable Long buchungId) {
        String requestId = kafkaResponseHandler.generateRequestId();
        kafkaRequestSender.sendRequest("zahlung.getByBuchungId", requestId, buchungId);
        return kafkaResponseHandler.getSingleResponse(requestId, ZahlungDTO.class);
    }
}
