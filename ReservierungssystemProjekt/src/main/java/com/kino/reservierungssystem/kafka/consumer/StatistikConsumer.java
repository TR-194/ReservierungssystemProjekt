package com.kino.reservierungssystem.kafka.consumer;

import com.kino.reservierungssystem.dto.StatistikDTO;
import com.kino.reservierungssystem.repository.BuchungRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StatistikConsumer {

    private final BuchungRepository buchungRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public StatistikConsumer(BuchungRepository buchungRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.buchungRepository = buchungRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Empfängt Kafka-Nachrichten für die Einnahmen einer Aufführung und sendet die Antwort zurück.
     */
    @KafkaListener(topics = "statistik.einnahmen.auffuehrung", groupId = "kino-group")
    public void handleEinnahmenAuffuehrung(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        Long auffuehrungId = (Long) request.get("data");

        Double sum = buchungRepository.sumEinnahmenByAuffuehrung(auffuehrungId);
        StatistikDTO statistikDTO = new StatistikDTO(sum != null ? sum : 0.0);

        kafkaTemplate.send("kino.response", Map.of("requestId", requestId, "data", statistikDTO));
    }

    /**
     * Empfängt Kafka-Nachrichten für die Einnahmen eines Films und sendet die Antwort zurück.
     */
    @KafkaListener(topics = "statistik.einnahmen.film", groupId = "kino-group")
    public void handleEinnahmenFilm(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        Long filmId = (Long) request.get("data");

        Double sum = buchungRepository.sumEinnahmenByFilm(filmId);
        StatistikDTO statistikDTO = new StatistikDTO(sum != null ? sum : 0.0);

        kafkaTemplate.send("kino.response", Map.of("requestId", requestId, "data", statistikDTO));
    }
}
