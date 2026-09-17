package org.spring.divas.venue.feature.venuetable;

import org.springframework.stereotype.Component;

@Component
public class VenueTableRequestMapper {
    public VenueTable toEntity(VenueTableRequestDto dto) {
        return VenueTable.builder()
                .venueId(dto.getVenueId())
                .isAvailable(dto.getIsAvailable())
                .build();
    }
}
