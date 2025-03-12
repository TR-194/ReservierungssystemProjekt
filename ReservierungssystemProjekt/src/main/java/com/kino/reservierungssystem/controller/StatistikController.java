package com.kino.reservierungssystem.controller;

import com.kino.reservierungssystem.dto.StatistikDTO;
import com.kino.reservierungssystem.kafka.request.KafkaRequestSender;
import com.kino.reservierungssystem.kafka.response.KafkaResponseHandler;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/statistiken")
public class StatistikController {

    private final KafkaRequestSender kafkaRequestSender;
    private final KafkaResponseHandler kafkaResponseHandler;

    public StatistikController(KafkaRequestSender kafkaRequestSender, KafkaResponseHandler kafkaResponseHandler) {
        this.kafkaRequestSender = kafkaRequestSender;
        this.kafkaResponseHandler = kafkaResponseHandler;
    }

    /**
     * Asynchrone Abfrage der Einnahmen pro Aufführung über Kafka.
     */
    @GetMapping("/einnahmen/auffuehrung/{auffuehrungId}")
    public CompletableFuture<StatistikDTO> getEinnahmenProAuffuehrung(@PathVariable Long auffuehrungId) {
        String requestId = kafkaResponseHandler.generateRequestId();
        kafkaRequestSender.sendRequest("statistik.einnahmen.auffuehrung", requestId, auffuehrungId);
        return kafkaResponseHandler.getSingleResponse(requestId, StatistikDTO.class);
    }

    /**
     * Asynchrone Abfrage der Einnahmen pro Film über Kafka.
     */
    @GetMapping("/einnahmen/film/{filmId}")
    public CompletableFuture<StatistikDTO> getEinnahmenProFilm(@PathVariable Long filmId) {
        String requestId = kafkaResponseHandler.generateRequestId();
        kafkaRequestSender.sendRequest("statistik.einnahmen.film", requestId, filmId);
        return kafkaResponseHandler.getSingleResponse(requestId, StatistikDTO.class);
    }
}
