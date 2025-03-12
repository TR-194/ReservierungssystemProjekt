package com.kino.reservierungssystem.kafka.consumer;

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

    @KafkaListener(topics = "kinosaal.getAll", groupId = "kino-group")
    public void getAllKinosaele(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");

        List<KinosaalDTO> kinosaele = kafkaRequestSender.sendRequest("db.kinosaal.getAll", null, List.class).join();
        kafkaResponseHandler.sendResponse(requestId, kinosaele);
    }

    @KafkaListener(topics = "kinosaal.update", groupId = "kino-group")
    public void updateKinosaal(Map<String, Object> request) {
        KinosaalDTO kinosaalDTO = (KinosaalDTO) request.get("data");
        kafkaRequestSender.sendRequest("db.kinosaal.update", kinosaalDTO, Void.class);
    }
}
