package com.kino.reservierungssystem.service;

import com.kino.reservierungssystem.dto.FilmDTO;
import com.kino.reservierungssystem.mapper.FilmMapper;
import com.kino.reservierungssystem.model.Film;
import com.kino.reservierungssystem.repository.FilmRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmRepository filmRepository;
    private final FilmMapper filmMapper = FilmMapper.INSTANCE;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public List<FilmDTO> getAllFilme() {
        return filmRepository.findAll().stream()
                .map(filmMapper::fromEntity)
                .collect(Collectors.toList());
    }

    public FilmDTO saveFilm(FilmDTO filmDTO) {
        Film film = filmMapper.toEntity(filmDTO);
        film = filmRepository.save(film);
        return filmMapper.fromEntity(film);
    }

    public FilmDTO updateFilm(Long id, FilmDTO filmDTO) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film not found"));
        film.setTitel(filmDTO.getTitel());
        film.setAlterbeschraenkung(filmDTO.getAlterbeschraenkung());
        film.setDauer(filmDTO.getDauer());
        Film updatedFilm = filmRepository.save(film);
        return filmMapper.fromEntity(updatedFilm);
    }

    public void deleteFilm(Long id) {
        filmRepository.deleteById(id);
    }

    public FilmDTO getFilmById(Long id) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film not found with id: " + id));
        return filmMapper.fromEntity(film);
    }

    public FilmDTO createFilm(FilmDTO filmDTO) {
        Film film = filmMapper.toEntity(filmDTO);
        film = filmRepository.save(film);
        return filmMapper.fromEntity(film);
    }
}