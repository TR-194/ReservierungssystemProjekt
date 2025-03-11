package com.kino.reservierungssystem.controller;

import com.kino.reservierungssystem.dto.AuffuehrungDTO;
import com.kino.reservierungssystem.kafka.producer.AuffuehrungProducer;
import com.kino.reservierungssystem.kafka.response.KafkaResponseHandler;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/auffuehrungen")
public class AuffuehrungController {

    private final AuffuehrungProducer auffuehrungProducer;
    private final KafkaResponseHandler kafkaResponseHandler;

    public AuffuehrungController(AuffuehrungProducer auffuehrungProducer, KafkaResponseHandler kafkaResponseHandler) {
        this.auffuehrungProducer = auffuehrungProducer;
        this.kafkaResponseHandler = kafkaResponseHandler;
    }

    /**
     * Asynchron alle Aufführungen abrufen.
     */
    @GetMapping
    public CompletableFuture<List<AuffuehrungDTO>> getAllAuffuehrungen() {
        String requestId = kafkaResponseHandler.generateRequestId();
        auffuehrungProducer.sendAuffuehrungAnfrage(requestId);
        return kafkaResponseHandler.getResponseList(requestId, AuffuehrungDTO.class);
    }

    /**
     * Asynchron eine Aufführung per ID abrufen.
     */
    @GetMapping("/{id}")
    public CompletableFuture<AuffuehrungDTO> getAuffuehrungById(@PathVariable Long id) {
        String requestId = kafkaResponseHandler.generateRequestId();
        auffuehrungProducer.sendAuffuehrungByIdAnfrage(requestId, id);
        return kafkaResponseHandler.getSingleResponse(requestId, AuffuehrungDTO.class);
    }

    /**
     * Asynchron eine neue Aufführung erstellen.
     */
    @PostMapping
    public void createAuffuehrung(@RequestBody AuffuehrungDTO auffuehrungDTO) {
        auffuehrungProducer.sendAuffuehrungErstellt(auffuehrungDTO);
    }

    /**
     * Asynchron eine Aufführung aktualisieren.
     */
    @PutMapping("/{id}")
    public void updateAuffuehrung(@PathVariable Long id, @RequestBody AuffuehrungDTO auffuehrungDTO) {
        auffuehrungDTO.setId(id);
        auffuehrungProducer.sendAuffuehrungAktualisiert(auffuehrungDTO);
    }

    /**
     * Asynchron eine Aufführung löschen.
     */
    @DeleteMapping("/{id}")
    public void deleteAuffuehrung(@PathVariable Long id) {
        auffuehrungProducer.sendAuffuehrungGeloescht(id);
    }
}
