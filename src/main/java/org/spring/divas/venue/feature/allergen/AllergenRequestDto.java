package org.spring.divas.venue.feature.allergen;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AllergenRequestDto(
        @NotBlank @Size(max = 100) String name
) {}
