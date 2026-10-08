package org.spring.divas.venue.feature.outbox;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
            kafkaTemplate.send("dish-events", event.getAggregateId(), event.getPayload())
                    .whenComplete((res, ex) -> {
                        if (ex == null) {
                            event.setProcessed(true);
                            outboxRepository.save(event);
                        }
                    });
        }
    }

}
