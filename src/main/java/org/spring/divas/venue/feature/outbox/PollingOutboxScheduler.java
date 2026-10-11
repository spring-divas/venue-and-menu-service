package org.spring.divas.venue.feature.outbox;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@AllArgsConstructor
public class PollingOutboxScheduler {
    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(fixedDelay = 500)
    @Transactional
    public void processOutbox() {
        List<OutboxEvent> pending = outboxRepository.findTop50ByProcessedFalseOrderByCreatedAtAsc();
        for (OutboxEvent event : pending) {
            ProducerRecord<String, String> record = new ProducerRecord<>(
                    "dish-events",
                    event.getAggregateId(),
                    event.getPayload());
            record.headers().add("eventType", event.getEventType().getBytes(StandardCharsets.UTF_8));
            record.headers().add("eventId", event.getId().toString().getBytes(StandardCharsets.UTF_8));
            kafkaTemplate.send(record).whenComplete((_, ex) -> {
                if (ex == null) {
                    event.setProcessed(true);
                    outboxRepository.save(event);
                }
            });
        }
    }
}
