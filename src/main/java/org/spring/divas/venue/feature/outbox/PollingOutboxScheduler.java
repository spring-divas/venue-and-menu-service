package org.spring.divas.venue.feature.outbox;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class PollingOutboxScheduler {
    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(fixedDelay = 500)
    @Transactional
    public void processOutbox() {
        List<OutboxEvent> pending = outboxRepository.findTop50ByProcessedFalseOrderByCreatedAtAsc();
        for (OutboxEvent event : pending) {
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(
                    "dish-events",
                    event.getAggregateId(),
                    event.getPayload());
            producerRecord.headers().add("eventType", event.getEventType().getBytes(StandardCharsets.UTF_8));
            producerRecord.headers().add("eventId", event.getId().toString().getBytes(StandardCharsets.UTF_8));
            kafkaTemplate.send(producerRecord).whenComplete((_, ex) -> {
                if (ex == null) {
                    event.setProcessed(true);
                    outboxRepository.save(event);
                    log.info("Published dish event: eventId={}, type={}", event.getId(), event.getEventType());
                } else {
                    log.error("Failed to publish dish event: eventId={}, type={}",
                            event.getId(), event.getEventType(), ex);
                }
            });
        }
    }
}
