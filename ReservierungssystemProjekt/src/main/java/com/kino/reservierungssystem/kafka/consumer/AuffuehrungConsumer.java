package com.kino.reservierungssystem.kafka.consumer;

import com.kino.reservierungssystem.dto.AuffuehrungDTO;
import com.kino.reservierungssystem.kafka.response.KafkaResponseHandler;
import com.kino.reservierungssystem.mapper.AuffuehrungMapper;
import com.kino.reservierungssystem.model.Auffuehrung;
import com.kino.reservierungssystem.repository.AuffuehrungRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuffuehrungConsumer {

    private final AuffuehrungRepository auffuehrungRepository;
    private final KafkaResponseHandler kafkaResponseHandler;
    private final AuffuehrungMapper auffuehrungMapper;

    public AuffuehrungConsumer(AuffuehrungRepository auffuehrungRepository, KafkaResponseHandler kafkaResponseHandler, AuffuehrungMapper auffuehrungMapper) {
        this.auffuehrungRepository = auffuehrungRepository;
        this.kafkaResponseHandler = kafkaResponseHandler;
        this.auffuehrungMapper = auffuehrungMapper;
    }

    @KafkaListener(topics = "kino.auffuehrung.getAll", groupId = "kino-group")
    public void handleAuffuehrungAnfrage(String requestId) {
        List<AuffuehrungDTO> auffuehrungen = auffuehrungRepository.findAll().stream()
                .map(auffuehrungMapper::fromEntity)
                .collect(Collectors.toList());
        kafkaResponseHandler.sendResponse(requestId, auffuehrungen);
    }

    @KafkaListener(topics = "kino.auffuehrung.getById", groupId = "kino-group")
    public void handleAuffuehrungByIdAnfrage(String requestId, Long id) {
        Auffuehrung auffuehrung = auffuehrungRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auffuehrung nicht gefunden mit ID: " + id));
        kafkaResponseHandler.sendResponse(requestId, auffuehrungMapper.fromEntity(auffuehrung));
    }

    @KafkaListener(topics = "kino.auffuehrung.create", groupId = "kino-group")
    public void handleAuffuehrungErstellt(AuffuehrungDTO auffuehrungDTO) {
        Auffuehrung auffuehrung = auffuehrungMapper.toEntity(auffuehrungDTO);
        auffuehrungRepository.save(auffuehrung);
    }

    @KafkaListener(topics = "kino.auffuehrung.update", groupId = "kino-group")
    public void handleAuffuehrungAktualisiert(AuffuehrungDTO auffuehrungDTO) {
        Auffuehrung auffuehrung = auffuehrungRepository.findById(auffuehrungDTO.getId())
                .orElseThrow(() -> new RuntimeException("Auffuehrung nicht gefunden mit ID: " + auffuehrungDTO.getId()));
        auffuehrung.setDatum(auffuehrungDTO.getDatum());
        auffuehrung.setUhrzeit(auffuehrungDTO.getUhrzeit());
        auffuehrungRepository.save(auffuehrung);
    }

    @KafkaListener(topics = "kino.auffuehrung.delete", groupId = "kino-group")
    public void handleAuffuehrungGeloescht(Long id) {
        auffuehrungRepository.deleteById(id);
    }
}
