package org.spring.divas.venue.feature.venuetable;

import org.springframework.stereotype.Component;

@Component
public class VenueTableResponseMapper {
    public VenueTableResponseDto toDto(VenueTable venueTable) {
        return VenueTableResponseDto.builder()
                .id(venueTable.getId())
                .venueId(venueTable.getVenueId())
                .isAvailable(venueTable.getIsAvailable())
                .build();
    }
}
