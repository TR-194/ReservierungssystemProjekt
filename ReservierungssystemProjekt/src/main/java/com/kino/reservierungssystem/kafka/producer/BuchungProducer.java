package com.kino.reservierungssystem.kafka.producer;

import com.kino.reservierungssystem.dto.BuchungDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class BuchungProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public BuchungProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendBuchungAnfrage(String requestId) {
        kafkaTemplate.send("kino.buchung.getAll", requestId);
    }

    public void sendBuchungByIdAnfrage(String requestId, Long id) {
        kafkaTemplate.send("kino.buchung.getById", requestId, id);
    }

    public void sendBuchungErstellt(BuchungDTO buchungDTO) {
        kafkaTemplate.send("kino.buchung.create", buchungDTO);
    }

    public void sendBuchungGeloescht(Long id) {
        kafkaTemplate.send("kino.buchung.delete", id);
    }
}
