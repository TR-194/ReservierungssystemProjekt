package com.kino.reservierungssystem.controller;

import com.kino.reservierungssystem.model.Film;
import com.kino.reservierungssystem.service.FilmService;
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
    public List<Film> getAllFilme() {
        return filmService.getAllFilme();
    }

    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        return filmService.saveFilm(film);
    }
}
