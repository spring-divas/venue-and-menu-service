package org.spring.divas.venue.feature.venue;

public record VenueResponseDto(
        Long id,
        String name,
        String address,
        Float rating,
        Integer reviewCount
) {}
