package com.kino.reservierungssystem.kafka.producer;

import com.kino.reservierungssystem.dto.AuffuehrungDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class AuffuehrungProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public AuffuehrungProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendAuffuehrungAnfrage(String requestId) {
        kafkaTemplate.send("kino.auffuehrung.getAll", requestId);
    }

    public void sendAuffuehrungByIdAnfrage(String requestId, Long id) {
        kafkaTemplate.send("kino.auffuehrung.getById", requestId, id);
    }

    public void sendAuffuehrungErstellt(AuffuehrungDTO auffuehrungDTO) {
        kafkaTemplate.send("kino.auffuehrung.create", auffuehrungDTO);
    }

    public void sendAuffuehrungAktualisiert(AuffuehrungDTO auffuehrungDTO) {
        kafkaTemplate.send("kino.auffuehrung.update", auffuehrungDTO);
    }

    public void sendAuffuehrungGeloescht(Long id) {
        kafkaTemplate.send("kino.auffuehrung.delete", id);
    }
}
