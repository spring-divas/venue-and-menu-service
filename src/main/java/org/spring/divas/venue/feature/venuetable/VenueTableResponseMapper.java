package org.spring.divas.venue.feature.venuetable;

import org.springframework.stereotype.Component;

@Component
public class VenueTableResponseMapper {
    public VenueTableResponseDto toDto(VenueTable venueTable) {
        return new VenueTableResponseDto(
                venueTable.getId(),
                venueTable.getVenueId(),
                venueTable.getIsAvailable()
        );
    }
}
