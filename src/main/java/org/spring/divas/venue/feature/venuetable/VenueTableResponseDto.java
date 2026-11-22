package org.spring.divas.venue.feature.venuetable;

public record VenueTableResponseDto(
        Long id,
        Long venueId,
        Boolean isAvailable
) {}
