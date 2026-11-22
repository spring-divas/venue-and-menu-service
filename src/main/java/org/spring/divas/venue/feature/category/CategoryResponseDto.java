package org.spring.divas.venue.feature.category;

public record CategoryResponseDto(
        Long id,
        Long venueId,
        String name
) {}
