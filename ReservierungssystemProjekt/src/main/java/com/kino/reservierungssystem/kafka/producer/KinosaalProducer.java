package com.kino.reservierungssystem.kafka.producer;

import com.kino.reservierungssystem.dto.KinosaalDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KinosaalProducer {

    private final KafkaTemplate<String, KinosaalDTO> kafkaTemplate;
    private final KafkaTemplate<String, Long> kafkaIdTemplate; // Für ID-Nachrichten

    public KinosaalProducer(KafkaTemplate<String, KinosaalDTO> kafkaTemplate, KafkaTemplate<String, Long> kafkaIdTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaIdTemplate = kafkaIdTemplate;
    }

    /**
     * Sendet einen neuen Kinosaal, wenn einer erstellt wurde.
     */
    public void sendKinosaalErstellt(KinosaalDTO kinosaalDTO) {
        kafkaTemplate.send("kinosaalCreated", kinosaalDTO);
    }

    /**
     * Sendet die ID eines gelöschten Kinosaals über ein separates Topic.
     */
    public void sendKinosaalGeloescht(Long id) {
        kafkaIdTemplate.send("kinosaalDeleted", id);
    }

    public void sendKinosaalToggleFreigabe(Long id) {
        kafkaIdTemplate.send("kinosaalToggleFreigabe", id);
    }
}