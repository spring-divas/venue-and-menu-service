package org.spring.divas.venue.feature.venuetable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VenueTableRequestDto {
    private Long venueId;
    private Boolean isAvailable;
}
