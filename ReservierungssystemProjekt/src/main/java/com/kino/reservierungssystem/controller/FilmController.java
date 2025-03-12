package com.kino.reservierungssystem.controller;

import com.kino.reservierungssystem.dto.FilmDTO;
import com.kino.reservierungssystem.kafka.producer.FilmProducer;
import com.kino.reservierungssystem.kafka.response.KafkaResponseHandler;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/filme")
public class FilmController {

    private final FilmProducer filmProducer;
    private final KafkaResponseHandler kafkaResponseHandler;

    public FilmController(FilmProducer filmProducer, KafkaResponseHandler kafkaResponseHandler) {
        this.filmProducer = filmProducer;
        this.kafkaResponseHandler = kafkaResponseHandler;
    }

    /**
     * Asynchron alle Filme abrufen.
     */
    @GetMapping
    public CompletableFuture<List<FilmDTO>> getAllFilme() {
        String requestId = kafkaResponseHandler.generateRequestId();
        filmProducer.sendFilmAnfrage(requestId);
        return kafkaResponseHandler.getResponseList(requestId, FilmDTO.class);
    }

    /**
     * Asynchron einen Film per ID abrufen.
     */
    @GetMapping("/{id}")
    public CompletableFuture<FilmDTO> getFilmById(@PathVariable Long id) {
        String requestId = kafkaResponseHandler.generateRequestId();
        filmProducer.sendFilmByIdAnfrage(requestId, id);
        return kafkaResponseHandler.getSingleResponse(requestId, FilmDTO.class);
    }

    /**
     * Asynchron einen neuen Film erstellen.
     */
    @PostMapping
    public void createFilm(@RequestBody FilmDTO filmDTO) {
        filmProducer.sendFilmErstellt(filmDTO);
    }

    /**
     * Asynchron einen Film aktualisieren.
     */
    @PutMapping("/{id}")
    public void updateFilm(@PathVariable Long id, @RequestBody FilmDTO filmDTO) {
        filmDTO.setId(id);
        filmProducer.sendFilmAktualisiert(filmDTO);
    }

    /**
     * Asynchron einen Film löschen.
     */
    @DeleteMapping("/{id}")
    public void deleteFilm(@PathVariable Long id) {
        filmProducer.sendFilmGeloescht(id);
    }
}
