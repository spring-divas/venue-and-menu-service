package org.spring.divas.venue.feature.dish;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class DishEventPublisher {
    private final KafkaTemplate<Long, DishUpdatedEvent> kafkaTemplate;

    public CompletableFuture<SendResult<Long, DishUpdatedEvent>> publish(
            DishUpdatedEvent event
    ) {
        ProducerRecord<Long, DishUpdatedEvent> record =
                new ProducerRecord<>("dish-events", event.dishId(), event);
        return kafkaTemplate.send(record);
    }
}
