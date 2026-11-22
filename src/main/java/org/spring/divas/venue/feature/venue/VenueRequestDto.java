package org.spring.divas.venue.feature.venue;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record VenueRequestDto(
        @NotBlank @Size(max = 150) String name,
        @NotBlank @Size(max = 255) String address
) {}
