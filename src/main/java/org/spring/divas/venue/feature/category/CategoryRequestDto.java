package org.spring.divas.venue.feature.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CategoryRequestDto(
        @NotNull Long venueId,
        @NotBlank @Size(max = 150) String name
) {}
