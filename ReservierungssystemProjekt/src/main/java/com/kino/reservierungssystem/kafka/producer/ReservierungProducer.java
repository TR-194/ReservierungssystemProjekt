package com.kino.reservierungssystem.kafka.producer;

import com.kino.reservierungssystem.dto.ReservierungDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class ReservierungProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ReservierungProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendReservierungErstellt(ReservierungDTO reservierungDTO) {
        kafkaTemplate.send("reservierung.create", reservierungDTO);
    }

    public void sendReservierungStorniert(Long reservierungsId) {
        kafkaTemplate.send("reservierung.cancel", reservierungsId);
    }

    public void sendReservierungUmgewandelt(Long reservierungsId) {
        String requestId = UUID.randomUUID().toString();
        Map<String, Object> message = Map.of(
                "requestId", requestId,
                "reservierungsId", reservierungsId
        );
        kafkaTemplate.send("reservierung.convert", message);
    }

    public void sendReservierungByEmail(String email) {
        kafkaTemplate.send("reservierung.findByEmail", email);
    }


}
