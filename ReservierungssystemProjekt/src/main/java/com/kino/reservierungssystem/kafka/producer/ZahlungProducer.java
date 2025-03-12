package com.kino.reservierungssystem.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ZahlungProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ZahlungProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Sendet eine Anfrage zur Erstellung einer Zahlung.
     */
    public void sendZahlungErstellt(String requestId, Object zahlungDTO) {
        kafkaTemplate.send("zahlung.create", requestId, zahlungDTO);
    }

    /**
     * Sendet eine Anfrage zum Abruf einer Zahlung für eine Buchung.
     */
    public void sendZahlungByBuchungIdAnfrage(String requestId, Long buchungId) {
        kafkaTemplate.send("zahlung.getByBuchungId", requestId, buchungId);
    }
}
