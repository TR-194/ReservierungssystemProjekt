package com.kino.reservierungssystem.controller;

import com.kino.reservierungssystem.dto.ReservierungDTO;
import com.kino.reservierungssystem.kafka.request.KafkaRequestSender;
import com.kino.reservierungssystem.kafka.response.KafkaResponseHandler;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/reservierungen")
public class ReservierungController {

    private final KafkaRequestSender kafkaRequestSender;
    private final KafkaResponseHandler kafkaResponseHandler;

    public ReservierungController(KafkaRequestSender kafkaRequestSender, KafkaResponseHandler kafkaResponseHandler) {
        this.kafkaRequestSender = kafkaRequestSender;
        this.kafkaResponseHandler = kafkaResponseHandler;
    }

    @GetMapping
    public CompletableFuture<List<ReservierungDTO>> getAllReservierungen() {
        String requestId = kafkaResponseHandler.generateRequestId();
        kafkaRequestSender.sendRequest("reservierung.getAll", requestId, null);
        return kafkaResponseHandler.getResponseList(requestId, ReservierungDTO.class);
    }

    @GetMapping("/{id}")
    public CompletableFuture<ReservierungDTO> getReservierungById(@PathVariable Long id) {
        String requestId = kafkaResponseHandler.generateRequestId();
        kafkaRequestSender.sendRequest("reservierung.getById", requestId, id);
        return kafkaResponseHandler.getSingleResponse(requestId, ReservierungDTO.class);
    }

    @PostMapping
    public void createReservierung(@RequestBody ReservierungDTO reservierungDTO) {
        kafkaRequestSender.sendRequest("reservierung.create", null, reservierungDTO);
    }

    @PutMapping("/{reservierungsId}/buchen")
    public void convertReservierungToBuchung(@PathVariable Long reservierungsId) {
        kafkaRequestSender.sendRequest("reservierung.convert", null, reservierungsId);
    }

    @DeleteMapping("/{reservierungsId}")
    public void cancelReservierung(@PathVariable Long reservierungsId) {
        kafkaRequestSender.sendRequest("reservierung.cancel", null, reservierungsId);
    }

    @GetMapping("/email/{email}")
    public CompletableFuture<List<ReservierungDTO>> getReservierungenByEmail(@PathVariable String email) {
        String requestId = kafkaResponseHandler.generateRequestId();
        kafkaRequestSender.sendRequest("reservierung.findByEmail", requestId, email);
        return kafkaResponseHandler.getResponseList(requestId,  ReservierungDTO.class);
    }
}