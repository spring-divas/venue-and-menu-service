package org.spring.divas.venue.feature.dish;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.spring.divas.venue.feature.outbox.OutboxEvent;
import org.springframework.beans.factory.annotation.Autowired;
import tools.jackson.databind.ObjectMapper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class DishEventMapper {
    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Mapping(target = "eventId", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "dishId", source = "id")
    public abstract DishUpdatedEvent toUpdatedEvent(Dish dish);

    @Mapping(target = "eventId", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "dishId", source = "id")
    public abstract DishCreatedEvent toCreatedEvent(Dish dish);

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
