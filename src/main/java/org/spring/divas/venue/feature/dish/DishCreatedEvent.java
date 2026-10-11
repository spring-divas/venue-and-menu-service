package org.spring.divas.venue.feature.dish;

import java.math.BigDecimal;
import java.util.UUID;

public record DishCreatedEvent(
        UUID eventId,
        Long dishId,
        Long venueId,
        String name,
        BigDecimal price
) {}
