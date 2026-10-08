package org.spring.divas.venue.feature.dish;

import lombok.AllArgsConstructor;
import org.spring.divas.venue.feature.outbox.OutboxEvent;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

@Component
@AllArgsConstructor
public class DishEventMapper {
    private final ObjectMapper objectMapper;

    public DishUpdatedEvent toUpdatedEvent(Dish dish) {
        return new DishUpdatedEvent(
                UUID.randomUUID(),
                dish.getId(),
                dish.getVenueId(),
                dish.getName(),
                dish.getPrice()
        );
    }

    public DishCreatedEvent toCreatedEvent(Dish dish) {
        return new DishCreatedEvent(
                UUID.randomUUID(),
                dish.getId(),
                dish.getVenueId(),
                dish.getName(),
                dish.getPrice()
        );
    }

    public OutboxEvent toOutboxEvent(DishUpdatedEvent event) {
        return OutboxEvent.builder()
                .id(event.eventId())
                .aggregateId(String.valueOf(event.dishId()))
                .eventType("DishUpdated")
                .payload(objectMapper.writeValueAsString(event))
                .build();
    }

    public OutboxEvent toOutboxEvent(DishCreatedEvent event) {
        return OutboxEvent.builder()
                .id(event.eventId())
                .aggregateId(String.valueOf(event.dishId()))
                .eventType("DishCreated")
                .payload(objectMapper.writeValueAsString(event))
                .build();
    }

}
