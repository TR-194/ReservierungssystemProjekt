package com.kino.reservierungssystem.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kino.reservierungssystem.dto.KinosaalDTO;
import com.kino.reservierungssystem.kafka.request.KafkaRequestSender;
import com.kino.reservierungssystem.kafka.response.KafkaResponseHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class KinosaalConsumer {

    private final KafkaRequestSender kafkaRequestSender;
    private final KafkaResponseHandler kafkaResponseHandler;

    public KinosaalConsumer(KafkaRequestSender kafkaRequestSender, KafkaResponseHandler kafkaResponseHandler) {
        this.kafkaRequestSender = kafkaRequestSender;
        this.kafkaResponseHandler = kafkaResponseHandler;
    }

    @KafkaListener(topics = "kinosaalGetAll", groupId = "kino-group")
    public void getAllKinosaele(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");

        List<KinosaalDTO> kinosaele = kafkaRequestSender.sendRequest("dbKinosaalGetAll", null, List.class).join();
        kafkaResponseHandler.sendResponse(requestId, kinosaele);
    }

    @KafkaListener(topics = "kinosaalUpdate", groupId = "kino-group")
    public void updateKinosaal(Map<String, Object> request) {
        KinosaalDTO kinosaalDTO = (KinosaalDTO) request.get("data");
        kafkaRequestSender.sendRequest("dbKinosaalUpdate", kinosaalDTO, Void.class);
    }

    @KafkaListener(topics = "kinosaalCreate", groupId = "kino-group")
    public void handleKinosaalErstellt(Map<String, Object> request) {
        System.out.println("Empfangene Kafka-Nachricht im Backend: " + request);

        if (request == null || !request.containsKey("data")) {
            System.err.println("Fehler: Kein 'data'-Feld in der Kafka-Nachricht!");
            return;
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            KinosaalDTO kinosaal = objectMapper.convertValue(request.get("data"), KinosaalDTO.class);

            if (kinosaal == null) {
                System.err.println("Fehler: kinosaal ist null nach der Deserialisierung!");
                return;
            }

            System.out.println("KinosaalDTO empfangen: " + kinosaal.getName());

            // ✅ Kinosaal inklusive Sitzreihen weiterleiten
            kafkaRequestSender.sendRequest("dbKinosaalCreate", kinosaal, Void.class);

        } catch (Exception e) {
            System.err.println("Fehler bei der Verarbeitung des Kinosaals: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @KafkaListener(topics = "kinosaalToggleFreigabe", groupId = "kino-group")
    public void handleKinosaalToggleFreigabe(Map<String, Object> request) {
        Long id = ((Number) request.get("data")).longValue();
        kafkaRequestSender.sendRequest("dbKinosaalToggleFreigabe", id, Void.class);
    }

    @KafkaListener(topics = "kinosaalDelete", groupId = "kino-group")
    public void handleKinosaalGeloescht(Map<String, Object> request) {
        Long id = ((Number) request.get("data")).longValue();
        kafkaRequestSender.sendRequest("dbKinosaalDelete", id, Void.class);
    }
}
