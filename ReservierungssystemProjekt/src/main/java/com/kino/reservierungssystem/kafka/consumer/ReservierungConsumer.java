package com.kino.reservierungssystem.kafka.consumer;

import com.kino.reservierungssystem.dto.ReservierungDTO;
import com.kino.reservierungssystem.mapper.ReservierungMapper;
import com.kino.reservierungssystem.model.Reservierung;
import com.kino.reservierungssystem.repository.ReservierungRepository;
import com.kino.reservierungssystem.kafka.response.KafkaResponseHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReservierungConsumer {

    private final ReservierungRepository reservierungRepository;
    private final ReservierungMapper reservierungMapper;
    private final KafkaResponseHandler kafkaResponseHandler;

    public ReservierungConsumer(ReservierungRepository reservierungRepository,
                                ReservierungMapper reservierungMapper,
                                KafkaResponseHandler kafkaResponseHandler) {
        this.reservierungRepository = reservierungRepository;
        this.reservierungMapper = reservierungMapper;
        this.kafkaResponseHandler = kafkaResponseHandler;
    }

    @KafkaListener(topics = "reservierung.getById", groupId = "kino-group")
    public void handleGetReservierungById(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        Long reservierungsId = ((Number) request.get("data")).longValue();

        Reservierung reservierung = reservierungRepository.findById(reservierungsId)
                .orElseThrow(() -> new RuntimeException("Reservierung nicht gefunden"));

        ReservierungDTO reservierungDTO = reservierungMapper.fromEntity(reservierung);

        kafkaResponseHandler.sendResponse(requestId, reservierungDTO);
    }

    @KafkaListener(topics = "reservierung.getAll", groupId = "kino-group")
    public void handleGetAllReservierungen(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");

        List<ReservierungDTO> reservierungen = reservierungRepository.findAll().stream()
                .map(reservierungMapper::fromEntity)
                .collect(Collectors.toList());

        kafkaResponseHandler.sendResponse(requestId, reservierungen);
    }
}
