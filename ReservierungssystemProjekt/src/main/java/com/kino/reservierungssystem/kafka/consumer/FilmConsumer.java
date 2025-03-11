package com.kino.reservierungssystem.kafka.consumer;

import com.kino.reservierungssystem.dto.FilmDTO;
import com.kino.reservierungssystem.kafka.response.KafkaResponseHandler;
import com.kino.reservierungssystem.mapper.FilmMapper;
import com.kino.reservierungssystem.model.Film;
import com.kino.reservierungssystem.repository.FilmRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FilmConsumer {

    private final FilmRepository filmRepository;
    private final KafkaResponseHandler kafkaResponseHandler;
    private final FilmMapper filmMapper;

    public FilmConsumer(FilmRepository filmRepository, KafkaResponseHandler kafkaResponseHandler, FilmMapper filmMapper) {
        this.filmRepository = filmRepository;
        this.kafkaResponseHandler = kafkaResponseHandler;
        this.filmMapper = filmMapper;
    }

    @KafkaListener(topics = "kino.film.getAll", groupId = "kino-group")
    public void handleFilmAnfrage(String requestId) {
        List<FilmDTO> filme = filmRepository.findAll().stream()
                .map(filmMapper::fromEntity)
                .collect(Collectors.toList());
        kafkaResponseHandler.sendResponse(requestId, filme);
    }

    @KafkaListener(topics = "kino.film.getById", groupId = "kino-group")
    public void handleFilmByIdAnfrage(String requestId, Long id) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film nicht gefunden mit ID: " + id));
        kafkaResponseHandler.sendResponse(requestId, filmMapper.fromEntity(film));
    }

    @KafkaListener(topics = "kino.film.create", groupId = "kino-group")
    public void handleFilmErstellt(FilmDTO filmDTO) {
        Film film = filmMapper.toEntity(filmDTO);
        filmRepository.save(film);
    }

    @KafkaListener(topics = "kino.film.update", groupId = "kino-group")
    public void handleFilmAktualisiert(FilmDTO filmDTO) {
        Film film = filmRepository.findById(filmDTO.getId())
                .orElseThrow(() -> new RuntimeException("Film nicht gefunden mit ID: " + filmDTO.getId()));
        film.setTitel(filmDTO.getTitel());
        film.setAlterbeschraenkung(filmDTO.getAlterbeschraenkung());
        film.setDauer(filmDTO.getDauer());
        filmRepository.save(film);
    }

    @KafkaListener(topics = "kino.film.delete", groupId = "kino-group")
    public void handleFilmGeloescht(Long id) {
        filmRepository.deleteById(id);
    }
}
