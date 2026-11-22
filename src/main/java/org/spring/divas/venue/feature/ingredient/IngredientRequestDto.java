package org.spring.divas.venue.feature.ingredient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

public record IngredientRequestDto(
        @NotBlank @Size(max = 100) String name,
        @PositiveOrZero @NotNull Integer quantity,
        Set<Long> allergens
) {
    public IngredientRequestDto {
        if (allergens == null) {
            allergens = new HashSet<>();
        }
    }
}
