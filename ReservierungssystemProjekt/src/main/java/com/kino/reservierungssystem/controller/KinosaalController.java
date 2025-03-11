package com.kino.reservierungssystem.controller;

import com.kino.reservierungssystem.dto.KinosaalDTO;
import com.kino.reservierungssystem.kafka.request.KafkaRequestSender;
import com.kino.reservierungssystem.kafka.response.KafkaResponseHandler;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/kinosaele")
public class KinosaalController {

    private final KafkaRequestSender kafkaRequestSender;
    private final KafkaResponseHandler kafkaResponseHandler;

    public KinosaalController(KafkaRequestSender kafkaRequestSender, KafkaResponseHandler kafkaResponseHandler) {
        this.kafkaRequestSender = kafkaRequestSender;
        this.kafkaResponseHandler = kafkaResponseHandler;
    }

    /**
     * Holt alle Kinosäle über Kafka.
     */
    @GetMapping
    public CompletableFuture<List<KinosaalDTO>> getAllKinoSaele() {
        String requestId = kafkaResponseHandler.generateRequestId();
        kafkaRequestSender.sendRequest("kinosaal.getAll", requestId, null);
        return kafkaResponseHandler.getResponseList(requestId, KinosaalDTO.class);
    }

    /**
     *  Holt einen spezifischen Kinosaal anhand seiner ID.
     */
    @GetMapping("/{id}")
    public CompletableFuture<KinosaalDTO> getKinoSaalById(@PathVariable Long id) {
        String requestId = kafkaResponseHandler.generateRequestId();
        kafkaRequestSender.sendRequest("kinosaal.getById", requestId, id);
        return kafkaResponseHandler.getSingleResponse(requestId, KinosaalDTO.class);
    }

    /**
     *  Erstellt einen neuen Kinosaal über Kafka.
     */
    @PostMapping
    public void createKinoSaal(@RequestBody KinosaalDTO kinosaalDTO) {
        kafkaRequestSender.sendRequest("kinosaal.create", null, kinosaalDTO);
    }

    /**
     *  Aktualisiert einen Kinosaal über Kafka.
     */
    @PutMapping("/{id}")
    public void updateKinoSaal(@PathVariable Long id, @RequestBody KinosaalDTO kinosaalDTO) {
        kinosaalDTO.setId(id);
        kafkaRequestSender.sendRequest("kinosaal.update", null, kinosaalDTO);
    }

    /**
     *  Löscht einen Kinosaal über Kafka.
     */
    @DeleteMapping("/{id}")
    public void deleteKinoSaal(@PathVariable Long id) {
        kafkaRequestSender.sendRequest("kinosaal.delete", null, id);
    }
}
