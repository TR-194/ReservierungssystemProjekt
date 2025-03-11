package com.kino.reservierungssystem.controller;

import com.kino.reservierungssystem.dto.BuchungDTO;
import com.kino.reservierungssystem.kafka.producer.BuchungProducer;
import com.kino.reservierungssystem.kafka.response.KafkaResponseHandler;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/buchungen")
public class BuchungController {

    private final BuchungProducer buchungProducer;
    private final KafkaResponseHandler kafkaResponseHandler;

    public BuchungController(BuchungProducer buchungProducer, KafkaResponseHandler kafkaResponseHandler) {
        this.buchungProducer = buchungProducer;
        this.kafkaResponseHandler = kafkaResponseHandler;
    }

    /**
     * Asynchron alle Buchungen abrufen.
     */
    @GetMapping
    public CompletableFuture<List<BuchungDTO>> getAllBuchungen() {
        String requestId = kafkaResponseHandler.generateRequestId();
        buchungProducer.sendBuchungAnfrage(requestId);
        return kafkaResponseHandler.getResponseList(requestId, BuchungDTO.class);
    }

    /**
     * Asynchron eine Buchung per ID abrufen.
     */
    @GetMapping("/{id}")
    public CompletableFuture<BuchungDTO> getBuchungById(@PathVariable Long id) {
        String requestId = kafkaResponseHandler.generateRequestId();
        buchungProducer.sendBuchungByIdAnfrage(requestId, id);
        return kafkaResponseHandler.getSingleResponse(requestId, BuchungDTO.class);
    }

    /**
     * Asynchron eine neue Buchung erstellen.
     */
    @PostMapping
    public void createBuchung(@RequestBody BuchungDTO buchungDTO) {
        buchungProducer.sendBuchungErstellt(buchungDTO);
    }

    /**
     * Asynchron eine Buchung löschen.
     */
    @DeleteMapping("/{id}")
    public void deleteBuchung(@PathVariable Long id) {
        buchungProducer.sendBuchungGeloescht(id);
    }
}
