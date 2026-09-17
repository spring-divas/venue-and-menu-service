package org.spring.divas.venue.feature.venue;

import org.springframework.stereotype.Component;

@Component
public class VenueResponseMapper {
    public VenueResponseDto toDto(Venue venue) {
        return VenueResponseDto.builder()
                .id(venue.getId())
                .name(venue.getName())
                .address(venue.getAddress())
                .rating(venue.getRating())
                .reviewCount(venue.getReviewCount())
                .build();
    }
}
