package org.spring.divas.venue.feature.venue;

import org.springframework.stereotype.Component;

@Component
public class VenueResponseMapper {
    public VenueResponseDto toDto(Venue venue) {
        return new VenueResponseDto(
                venue.getId(),
                venue.getName(),
                venue.getAddress(),
                venue.getRating(),
                venue.getReviewCount()
        );
    }
}
