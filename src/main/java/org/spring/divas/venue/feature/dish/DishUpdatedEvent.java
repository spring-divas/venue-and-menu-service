package org.spring.divas.venue.feature.dish;

import java.math.BigDecimal;
import java.util.UUID;

public record DishUpdatedEvent(
        UUID eventId,
        UUID dishId,
        UUID venueID,
        String name,
        BigDecimal price
) {}
