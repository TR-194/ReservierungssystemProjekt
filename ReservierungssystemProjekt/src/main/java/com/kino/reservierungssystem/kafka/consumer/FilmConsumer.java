package com.kino.reservierungssystem.kafka.consumer;

import com.kino.reservierungssystem.dto.FilmDTO;
import com.kino.reservierungssystem.kafka.request.KafkaRequestSender;
import com.kino.reservierungssystem.kafka.response.KafkaResponseHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FilmConsumer {

    private final KafkaRequestSender kafkaRequestSender;
    private final KafkaResponseHandler kafkaResponseHandler;

    public FilmConsumer(KafkaRequestSender kafkaRequestSender, KafkaResponseHandler kafkaResponseHandler) {
        this.kafkaRequestSender = kafkaRequestSender;
        this.kafkaResponseHandler = kafkaResponseHandler;
    }

    @KafkaListener(topics = "film.getById", groupId = "kino-group")
    public void handleGetFilmById(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        Long filmId = ((Number) request.get("data")).longValue();

        FilmDTO film = kafkaRequestSender.sendRequest("db.film.getById", filmId, FilmDTO.class).join();
        kafkaResponseHandler.sendResponse(requestId, film);
    }

    @KafkaListener(topics = "film.getAll", groupId = "kino-group")
    public void handleGetAllFilme(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");

        List<FilmDTO> filme = kafkaRequestSender.sendRequest("db.film.getAll", null, List.class).join();
        kafkaResponseHandler.sendResponse(requestId, filme);
    }
}
