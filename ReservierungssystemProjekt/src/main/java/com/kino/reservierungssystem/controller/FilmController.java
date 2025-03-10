package com.kino.reservierungssystem.controller;


import com.kino.reservierungssystem.dto.FilmDTO;
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
    public List<FilmDTO> getAllFilme() {
        return filmService.getAllFilme();
    }

    @GetMapping("/{id}")
    public FilmDTO getFilmById(@PathVariable Long id) {
        return filmService.getFilmById(id);
    }

    @PostMapping
    public FilmDTO createFilm(@RequestBody FilmDTO filmDTO) {
        return filmService.createFilm(filmDTO);
    }

    @PutMapping("/{id}")
    public FilmDTO updateFilm(@PathVariable Long id, @RequestBody FilmDTO filmDTO) {
        return filmService.updateFilm(id, filmDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteFilm(@PathVariable Long id) {
        filmService.deleteFilm(id);
    }
}