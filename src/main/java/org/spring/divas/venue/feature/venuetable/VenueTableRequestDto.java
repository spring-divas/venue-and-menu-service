package org.spring.divas.venue.feature.venuetable;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VenueTableRequestDto {
    @NotNull
    private Long venueId;

    @NotNull
    private Boolean isAvailable;
}
