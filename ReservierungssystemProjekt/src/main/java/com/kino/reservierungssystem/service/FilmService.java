package com.kino.reservierungssystem.service;

import com.kino.reservierungssystem.model.Film;
import com.kino.reservierungssystem.repository.FilmRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FilmService {
    private final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public List<Film> getAllFilme() {
        return filmRepository.findAll();
    }

    public Film saveFilm(Film film) {
        return filmRepository.save(film);
    }
}
