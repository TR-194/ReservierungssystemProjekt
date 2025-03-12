package com.kino.reservierungssystem.kafka.consumer;

import com.kino.reservierungssystem.dto.ZahlungDTO;
import com.kino.reservierungssystem.model.Zahlung;
import com.kino.reservierungssystem.repository.ZahlungRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public class ZahlungConsumer {

    private final ZahlungRepository zahlungRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ZahlungConsumer(ZahlungRepository zahlungRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.zahlungRepository = zahlungRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Empfängt Kafka-Nachrichten für neue Zahlungen und speichert sie in der Datenbank.
     */
    @KafkaListener(topics = "zahlung.create", groupId = "kino-group")
    public void handleZahlungErstellung(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        ZahlungDTO zahlungDTO = (ZahlungDTO) request.get("data");

        Zahlung zahlung = new Zahlung();
        zahlung.setBetrag(zahlungDTO.getBetrag());
        zahlung.setZahlungsdatum(LocalDate.now());
        zahlung.setBuchungId(zahlungDTO.getBuchungId());

        zahlung = zahlungRepository.save(zahlung);
        ZahlungDTO responseDTO = new ZahlungDTO(zahlung.getId(), zahlung.getBetrag(), zahlung.getZahlungsdatum(), zahlung.getBuchungId());

        kafkaTemplate.send("kino.response", Map.of("requestId", requestId, "data", responseDTO));
    }

    /**
     * Empfängt Kafka-Nachrichten, um eine Zahlung basierend auf der Buchungs-ID zu finden.
     */
    @KafkaListener(topics = "zahlung.getByBuchungId", groupId = "kino-group")
    public void handleZahlungByBuchungId(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        Long buchungId = (Long) request.get("data");

        Zahlung zahlung = zahlungRepository.findByBuchungId(buchungId);
        ZahlungDTO responseDTO = (zahlung != null) ?
                new ZahlungDTO(zahlung.getId(), zahlung.getBetrag(), zahlung.getZahlungsdatum(), zahlung.getBuchungId()) : null;

        kafkaTemplate.send("kino.response", Map.of("requestId", requestId, "data", responseDTO));
    }
}
