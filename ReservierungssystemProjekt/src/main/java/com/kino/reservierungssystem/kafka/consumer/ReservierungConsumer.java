package com.kino.reservierungssystem.kafka.consumer;

import com.kino.reservierungssystem.dto.ReservierungDTO;
import com.kino.reservierungssystem.mapper.ReservierungMapper;
import com.kino.reservierungssystem.model.Buchung;
import com.kino.reservierungssystem.model.Reservierung;
import com.kino.reservierungssystem.repository.BuchungRepository;
import com.kino.reservierungssystem.repository.ReservierungRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservierungConsumer {

    private final ReservierungRepository reservierungRepository;
    private final BuchungRepository buchungRepository;
    private final ReservierungMapper reservierungMapper;

    public ReservierungConsumer(ReservierungRepository reservierungRepository,
                                BuchungRepository buchungRepository,
                                ReservierungMapper reservierungMapper) {
        this.reservierungRepository = reservierungRepository;
        this.buchungRepository = buchungRepository;
        this.reservierungMapper = reservierungMapper;
    }

    /**
     * Konsumiert eine Kafka-Nachricht, um alle Reservierungen abzurufen.
     */
    @KafkaListener(topics = "reservierung.getAll", groupId = "kino-group")
    public List<ReservierungDTO> handleGetAllReservierungen() {
        return reservierungRepository.findAll().stream()
                .map(reservierungMapper::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Konsumiert eine Kafka-Nachricht, um eine Reservierung nach ID abzurufen.
     */
    @KafkaListener(topics = "reservierung.getById", groupId = "kino-group")
    public ReservierungDTO handleGetReservierungById(Long reservierungsId) {
        Reservierung reservierung = reservierungRepository.findById(reservierungsId)
                .orElseThrow(() -> new RuntimeException("Reservierung nicht gefunden"));
        return reservierungMapper.fromEntity(reservierung);
    }

    /**
     * Konsumiert eine Kafka-Nachricht, um eine neue Reservierung zu speichern.
     */
    @KafkaListener(topics = "reservierung.create", groupId = "kino-group")
    public void handleCreateReservierung(ReservierungDTO reservierungDTO) {
        Reservierung reservierung = reservierungMapper.toEntity(reservierungDTO);
        reservierungRepository.save(reservierung);
    }

    /**
     * Konsumiert eine Kafka-Nachricht, um eine Reservierung zu löschen.
     */
    @KafkaListener(topics = "reservierung.delete", groupId = "kino-group")
    public void handleDeleteReservierung(Long reservierungsId) {
        reservierungRepository.deleteById(reservierungsId);
    }

    /**
     * Konvertiert eine Reservierung in eine Buchung.
     */
    @KafkaListener(topics = "reservierung.convert", groupId = "kino-group")
    public void handleReservierungInBuchung(Long reservierungsId) {
        Reservierung reservierung = reservierungRepository.findById(reservierungsId)
                .orElseThrow(() -> new RuntimeException("Reservierung nicht gefunden"));

        Buchung buchung = reservierung.inBuchungUmwandeln();

        // Speichert die Buchung statt der Reservierung
        buchungRepository.save(buchung);

        // Optional: Löscht die ursprüngliche Reservierung nach erfolgreicher Konvertierung
        reservierungRepository.deleteById(reservierungsId);
    }

    /**
     * Holt eine Reservierung anhand der E-Mail-Adresse des Kunden.
     */
    @KafkaListener(topics = "reservierung.getByEmail", groupId = "kino-group")
    public List<ReservierungDTO> handleGetReservierungByEmail(String email) {
        List<Reservierung> reservierungen = reservierungRepository.findByEmail(email);
        return reservierungen.stream()
                .map(reservierungMapper::fromEntity)
                .collect(Collectors.toList());
    }
}
