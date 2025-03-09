package com.kino.reservierungssystem.service;

import com.kino.reservierungssystem.model.Buchung;
import com.kino.reservierungssystem.model.Reservierung;
import com.kino.reservierungssystem.repository.ReservierungRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReservierungService {
    private final ReservierungRepository reservierungRepository;

    public ReservierungService(ReservierungRepository reservierungRepository) {
        this.reservierungRepository = reservierungRepository;
    }

    public List<Reservierung> getReservierungenByKunde(Long kundeId) {
        return reservierungRepository.findByKundeId(kundeId);
    }

    public Reservierung saveReservierung(Reservierung reservierung) {
        return reservierungRepository.save(reservierung);
    }

    public Buchung convertToBuchung(Long reservierungsId) {
        Reservierung reservierung = reservierungRepository.findById(reservierungsId)
                .orElseThrow(() -> new RuntimeException("Reservierung not found"));
        return reservierung.inBuchungUmwandeln();
    }

    public void cancelReservierung(Long reservierungsId) {
        Reservierung reservierung = reservierungRepository.findById(reservierungsId)
                .orElseThrow(() -> new RuntimeException("Reservierung not found"));
        reservierung.stornieren();
        reservierungRepository.save(reservierung);
    }
}
