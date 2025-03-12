package com.kino.reservierungssystem.kafka.producer;

import com.kino.reservierungssystem.dto.FilmDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class FilmProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public FilmProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendFilmAnfrage(String requestId) {
        kafkaTemplate.send("kino.film.getAll", requestId);
    }

    public void sendFilmByIdAnfrage(String requestId, Long id) {
        kafkaTemplate.send("kino.film.getById", requestId, id);
    }

    public void sendFilmErstellt(FilmDTO filmDTO) {
        kafkaTemplate.send("kino.film.create", filmDTO);
    }

    public void sendFilmAktualisiert(FilmDTO filmDTO) {
        kafkaTemplate.send("kino.film.update", filmDTO);
    }

    public void sendFilmGeloescht(Long id) {
        kafkaTemplate.send("kino.film.delete", id);
    }
}
