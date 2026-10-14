package org.spring.divas.venue.feature.venue;

import org.springframework.stereotype.Component;

@Component
public class VenueRequestMapper {
    public Venue toEntity(VenueRequestDto dto) {
        return Venue.builder()
                .name(dto.getName())
                .address(dto.getAddress())
                .build();
    }
}
