package org.spring.divas.venue.feature.dish.kafka;

import lombok.RequiredArgsConstructor;
import org.spring.divas.venue.feature.dish.Dish;
import org.spring.divas.venue.feature.dish.DishChangePublisher;
import org.spring.divas.venue.feature.dish.DishCreatedEvent;
import org.spring.divas.venue.feature.dish.DishEventMapper;
import org.spring.divas.venue.feature.dish.DishUpdatedEvent;
import org.spring.divas.venue.feature.outbox.OutboxEvent;
import org.spring.divas.venue.feature.outbox.OutboxRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        name = "dish.sync.transport",
        havingValue = "kafka",
        matchIfMissing = true
)
@RequiredArgsConstructor
public class KafkaDishChangePublisher implements DishChangePublisher {
    private final DishEventMapper dishEventMapper;
    private final OutboxRepository outboxRepository;

    @Override
    public void publishCreated(Dish dish) {
        DishCreatedEvent event = dishEventMapper.toCreatedEvent(dish);
        save(dishEventMapper.toOutboxEvent(event));
    }

    @Override
    public void publishUpdated(Dish dish) {
        DishUpdatedEvent event = dishEventMapper.toUpdatedEvent(dish);
        save(dishEventMapper.toOutboxEvent(event));
    }

    private void save(OutboxEvent event) {
        outboxRepository.save(event);
    }
}
