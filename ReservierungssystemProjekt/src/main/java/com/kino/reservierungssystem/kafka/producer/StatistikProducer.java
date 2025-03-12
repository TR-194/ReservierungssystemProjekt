package com.kino.reservierungssystem.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class StatistikProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public StatistikProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Sendet eine Anfrage zur Abfrage der Einnahmen einer bestimmten Aufführung.
     */
    public void sendEinnahmenAuffuehrungAnfrage(String requestId, Long auffuehrungId) {
        kafkaTemplate.send("statistik.einnahmen.auffuehrung", requestId, auffuehrungId);
    }

    /**
     * Sendet eine Anfrage zur Abfrage der Einnahmen eines bestimmten Films.
     */
    public void sendEinnahmenFilmAnfrage(String requestId, Long filmId) {
        kafkaTemplate.send("statistik.einnahmen.film", requestId, filmId);
    }
}
