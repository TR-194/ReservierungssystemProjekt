package com.kino.reservierungssystem.kafka.consumer;

import com.kino.reservierungssystem.dto.FilmDTO;
import com.kino.reservierungssystem.mapper.FilmMapper;
import com.kino.reservierungssystem.model.Film;
import com.kino.reservierungssystem.repository.FilmRepository;
import com.kino.reservierungssystem.kafka.response.KafkaResponseHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FilmConsumer {

    private final FilmRepository filmRepository;
    private final FilmMapper filmMapper;
    private final KafkaResponseHandler kafkaResponseHandler;

    public FilmConsumer(FilmRepository filmRepository, FilmMapper filmMapper, KafkaResponseHandler kafkaResponseHandler) {
        this.filmRepository = filmRepository;
        this.filmMapper = filmMapper;
        this.kafkaResponseHandler = kafkaResponseHandler;
    }

    @KafkaListener(topics = "film.getById", groupId = "kino-group")
    public void handleGetFilmById(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        Long filmId = ((Number) request.get("data")).longValue();

        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new RuntimeException("Film nicht gefunden"));

        FilmDTO filmDTO = filmMapper.fromEntity(film);

        kafkaResponseHandler.sendResponse(requestId, filmDTO);
    }

    @KafkaListener(topics = "film.getAll", groupId = "kino-group")
    public void handleGetAllFilme(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");

        List<FilmDTO> filme = filmRepository.findAll().stream()
                .map(filmMapper::fromEntity)
                .collect(Collectors.toList());

        kafkaResponseHandler.sendResponse(requestId, filme);
    }
}
