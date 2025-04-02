package ru.otus.java.pro.mt.core.transfers.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "mt.transfers.status.info", groupId = "notification-group")
    public void listen(String message) {
        log.info("Received message from Kafka: {}", message);

        try {
            JsonNode jsonNode = objectMapper.readTree(message);
            String transferId = jsonNode.get("transferId").asText();
            log.info("По переводу {} клиенту отправлена нотификация", transferId);
        } catch (Exception e) {
            log.error("Failed to process message from Kafka: {}", message, e);
        }
    }
}