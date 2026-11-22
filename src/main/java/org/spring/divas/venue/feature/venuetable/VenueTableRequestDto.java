package org.spring.divas.venue.feature.venuetable;

import jakarta.validation.constraints.NotNull;

public record VenueTableRequestDto(
        @NotNull Long venueId,
        @NotNull Boolean isAvailable
) {}
