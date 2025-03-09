package com.kino.reservierungssystem.controller;

import com.kino.reservierungssystem.model.Film;
import com.kino.reservierungssystem.service.FilmService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/filme")
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public ResponseEntity<List<Film>> getAllFilme() {
        List<Film> filme = filmService.getAllFilme();
        return ResponseEntity.ok(filme);
    }

    @PostMapping
    public ResponseEntity<Film> createFilm(@RequestBody Film film) {
        Film savedFilm = filmService.saveFilm(film);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFilm);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Film> updateFilm(@PathVariable Long id, @RequestBody Film filmDetails) {
        Film updatedFilm = filmService.updateFilm(id, filmDetails);
        return ResponseEntity.ok(updatedFilm);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable Long id) {
        filmService.deleteFilm(id);
        return ResponseEntity.noContent().build();
    }
}
