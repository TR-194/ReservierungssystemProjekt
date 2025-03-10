package com.kino.reservierungssystem.service;

import com.kino.reservierungssystem.dto.ReservierungDTO;
import com.kino.reservierungssystem.mapper.ReservierungMapper;
import com.kino.reservierungssystem.model.*;
import com.kino.reservierungssystem.repository.ReservierungRepository;
import com.kino.reservierungssystem.repository.SitzplatzRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.kino.reservierungssystem.model.Kategorie.*;

@Service
public class ReservierungService {
    private final ReservierungRepository reservierungRepository;
    private final SitzplatzRepository sitzplatzRepository;
    private final ReservierungMapper reservierungMapper;

    public ReservierungService(ReservierungRepository reservierungRepository, SitzplatzRepository sitzplatzRepository, ReservierungMapper reservierungMapper) {
        this.reservierungRepository = reservierungRepository;
        this.sitzplatzRepository = sitzplatzRepository;
        this.reservierungMapper = reservierungMapper;
    }

    public List<ReservierungDTO> getAllReservierungen() {
        return reservierungRepository.findAll().stream()
                .map(reservierungMapper::fromEntity)
                .collect(Collectors.toList());
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

    public double berechnePreis(Auffuehrung auffuehrung, List<Long> sitzplaetze) {
        Preismodell preismodell = auffuehrung.getPreismodell();
        double gesamtpreis = 0;

        for (Long sitzplatzId : sitzplaetze) {
            Sitzplatz sitzplatz = sitzplatzRepository.findById(sitzplatzId)
                    .orElseThrow(() -> new RuntimeException("Sitzplatz nicht gefunden"));

            switch (sitzplatz.getSitzreihe().getKategorie()) {
                case LOGE_MIT_SERVICE:
                    gesamtpreis += preismodell.getLogeMitServicePreis();
                    break;
                case LOGE:
                    gesamtpreis += preismodell.getLogePreis();
                    break;
                case PARKETT:
                    gesamtpreis += preismodell.getParkettPreis();
                    break;
            }
        }

        return gesamtpreis;
    }
}