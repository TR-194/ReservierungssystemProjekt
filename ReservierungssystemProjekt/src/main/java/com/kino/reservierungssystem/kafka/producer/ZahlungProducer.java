package com.kino.reservierungssystem.kafka.producer;

import com.kino.reservierungssystem.dto.ZahlungDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ZahlungProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ZahlungProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    /**
     * Sendet eine Anfrage zum Abruf einer Zahlung für eine Buchung.
     */
    public void sendZahlungByBuchungIdAnfrage(String requestId, Long buchungId) {
        kafkaTemplate.send("zahlungGetByBuchungId", requestId, buchungId);
    }

    public void sendZahlungErstellt(ZahlungDTO zahlungDTO) {
        kafkaTemplate.send("zahlungCreate", zahlungDTO);
    }
}
