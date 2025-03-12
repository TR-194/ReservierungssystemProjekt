package com.kinoDbAccess.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class KafkaResponseSender {

    public void setKafkaTemplate(KafkaTemplate<String, Map<String, Object>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    private KafkaTemplate<String, Map<String, Object>> kafkaTemplate;

    public KafkaResponseSender(KafkaTemplate<String, Map<String, Object>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendResponse(String topic, String requestId, Object data) {
        Map<String, Object> message = Map.of("requestId", requestId, "data", data);
        kafkaTemplate.send(topic, message);
    }
}