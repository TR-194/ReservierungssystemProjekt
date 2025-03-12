package com.kino.reservierungssystem.kafka.consumer;

import com.kino.reservierungssystem.dto.BuchungDTO;
import com.kino.reservierungssystem.kafka.response.KafkaResponseHandler;
import com.kino.reservierungssystem.mapper.BuchungMapper;
import com.kino.reservierungssystem.model.Buchung;
import com.kino.reservierungssystem.repository.BuchungRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BuchungConsumer {

    private final BuchungRepository buchungRepository;
    private final KafkaResponseHandler kafkaResponseHandler;
    private final BuchungMapper buchungMapper;

    public BuchungConsumer(BuchungRepository buchungRepository, KafkaResponseHandler kafkaResponseHandler, BuchungMapper buchungMapper) {
        this.buchungRepository = buchungRepository;
        this.kafkaResponseHandler = kafkaResponseHandler;
        this.buchungMapper = buchungMapper;
    }

    @KafkaListener(topics = "kino.buchung.getAll", groupId = "kino-group")
    public void handleBuchungAnfrage(String requestId) {
        List<BuchungDTO> buchungen = buchungRepository.findAll().stream()
                .map(buchungMapper::fromEntity)
                .collect(Collectors.toList());
        kafkaResponseHandler.sendResponse(requestId, buchungen);
    }

    @KafkaListener(topics = "kino.buchung.getById", groupId = "kino-group")
    public void handleBuchungByIdAnfrage(String requestId, Long id) {
        Buchung buchung = buchungRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Buchung nicht gefunden mit ID: " + id));
        kafkaResponseHandler.sendResponse(requestId, buchungMapper.fromEntity(buchung));
    }

    @KafkaListener(topics = "kino.buchung.create", groupId = "kino-group")
    public void handleBuchungErstellt(BuchungDTO buchungDTO) {
        Buchung buchung = buchungMapper.toEntity(buchungDTO);
        buchungRepository.save(buchung);
    }

    @KafkaListener(topics = "kino.buchung.delete", groupId = "kino-group")
    public void handleBuchungGeloescht(Long id) {
        buchungRepository.deleteById(id);
    }
}
