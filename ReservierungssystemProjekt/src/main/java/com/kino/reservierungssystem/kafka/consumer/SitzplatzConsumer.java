package com.kino.reservierungssystem.kafka.consumer;

import com.kino.reservierungssystem.dto.SitzplatzDTO;
import com.kino.reservierungssystem.model.Sitzplatz;
import com.kino.reservierungssystem.model.Status;
import com.kino.reservierungssystem.repository.SitzplatzRepository;
import com.kino.reservierungssystem.kafka.response.KafkaResponseHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SitzplatzConsumer {

    private final SitzplatzRepository sitzplatzRepository;
    private final KafkaResponseHandler kafkaResponseHandler;

    public SitzplatzConsumer(SitzplatzRepository sitzplatzRepository, KafkaResponseHandler kafkaResponseHandler) {
        this.sitzplatzRepository = sitzplatzRepository;
        this.kafkaResponseHandler = kafkaResponseHandler;
    }

    @KafkaListener(topics = "sitzplatz.getAll", groupId = "kino-group")
    public void handleGetAllSitzplaetze(String requestId) {
        List<SitzplatzDTO> sitzplaetze = sitzplatzRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        kafkaResponseHandler.sendResponse(requestId, sitzplaetze);
    }

    @KafkaListener(topics = "sitzplatz.getById", groupId = "kino-group")
    public void handleGetSitzplatzById(String requestId, Long id) {
        Sitzplatz sitzplatz = sitzplatzRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sitzplatz nicht gefunden"));
        kafkaResponseHandler.sendResponse(requestId, convertToDTO(sitzplatz));
    }

    @KafkaListener(topics = "sitzplatz.updateStatus", groupId = "kino-group")
    public void handleUpdateSitzplatzStatus(String requestId, SitzplatzDTO sitzplatzDTO) {
        Sitzplatz sitzplatz = sitzplatzRepository.findById(sitzplatzDTO.getId())
                .orElseThrow(() -> new RuntimeException("Sitzplatz nicht gefunden"));

        sitzplatz.setStatus(Status.valueOf(sitzplatzDTO.getStatus().toUpperCase())); // Convert String to Enum
        sitzplatzRepository.save(sitzplatz);

        kafkaResponseHandler.sendResponse(requestId, convertToDTO(sitzplatz));
    }

    private SitzplatzDTO convertToDTO(Sitzplatz sitzplatz) {
        return new SitzplatzDTO(
                sitzplatz.getId(),
                sitzplatz.getPlatzNr(),
                sitzplatz.getStatus().toString(), // Convert Enum to String
                sitzplatz.getAuffuehrungId(),
                sitzplatz.getKategorieId()
        );
    }
}
