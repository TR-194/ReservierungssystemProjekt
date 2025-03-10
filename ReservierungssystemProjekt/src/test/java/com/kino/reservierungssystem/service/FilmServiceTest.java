package com.kino.reservierungssystem.service;

import com.kino.reservierungssystem.dto.FilmDTO;
import com.kino.reservierungssystem.mapper.FilmMapper;
import com.kino.reservierungssystem.model.Film;
import com.kino.reservierungssystem.repository.FilmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FilmServiceTest {

    @Mock
    private FilmRepository filmRepository;

    @InjectMocks
    private FilmService filmService;

    private final FilmMapper filmMapper = FilmMapper.INSTANCE; // Direkte Instanz verwenden

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllFilme_returnsListOfFilms() {
        // Arrange
        Film film1 = new Film(1L, "Film A", "PG", 120, null);
        Film film2 = new Film(2L, "Film B", "PG-13", 90, null);
        when(filmRepository.findAll()).thenReturn(Arrays.asList(film1, film2));

        // Act
        List<FilmDTO> filme = filmService.getAllFilme();

        // Assert
        assertEquals(2, filme.size());
        assertEquals("Film A", filme.get(0).getTitel());
        assertEquals("Film B", filme.get(1).getTitel());
        verify(filmRepository, times(1)).findAll();
    }

    @Test
    void updateFilm_updatesExistingFilm() {
        // Arrange
        Film existingFilm = new Film(1L, "Old Title", "PG", 100, null);
        FilmDTO updateDetails = new FilmDTO(null, "New Title", "R", 110);
        when(filmRepository.findById(1L)).thenReturn(Optional.of(existingFilm));
        when(filmRepository.save(any(Film.class))).thenAnswer(invocation -> invocation.getArgument(0)); // Speichert das geänderte Objekt direkt

        // Act
        FilmDTO updatedFilm = filmService.updateFilm(1L, updateDetails);

        // Assert
        assertNotNull(updatedFilm);
        assertEquals("New Title", updatedFilm.getTitel());
        assertEquals("R", updatedFilm.getAlterbeschraenkung());
        assertEquals(110, updatedFilm.getDauer());
        verify(filmRepository, times(1)).findById(1L);
        verify(filmRepository, times(1)).save(any(Film.class));
    }
}
