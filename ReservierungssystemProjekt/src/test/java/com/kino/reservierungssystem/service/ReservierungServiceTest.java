package com.kino.reservierungssystem.service;

import com.kino.reservierungssystem.model.Buchung;
import com.kino.reservierungssystem.model.Reservierung;
import com.kino.reservierungssystem.repository.ReservierungRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservierungServiceTest {

    @Mock
    private ReservierungRepository reservierungRepository;

    @InjectMocks
    private ReservierungService reservierungService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void convertToBuchung_validReservierung_returnsBuchung() {
        // Arrange: Erstelle eine gültige Reservierung (vereinfacht)
        Reservierung reservierung = new Reservierung();
        reservierung.setId(1L);
        reservierung.setAblaufDatum(LocalDate.now().plusDays(1));

        // Simuliere, dass die Methode inBuchungUmwandeln() eine neue Buchung erzeugt
        Buchung buchung = new Buchung();
        // Erstelle einen Spy, damit wir die Methode inBuchungUmwandeln() überschreiben können
        Reservierung spyReservierung = spy(reservierung);
        doReturn(buchung).when(spyReservierung).inBuchungUmwandeln();

        // Wenn findById(1L) aufgerufen wird, gebe den Spy zurück
        when(reservierungRepository.findById(1L)).thenReturn(Optional.of(spyReservierung));

        // Act
        Buchung result = reservierungService.convertToBuchung(1L);

        // Assert
        assertNotNull(result);
        // Prüfe, ob findById(1L) genau einmal aufgerufen wurde
        verify(reservierungRepository, times(1)).findById(1L);
    }
}
