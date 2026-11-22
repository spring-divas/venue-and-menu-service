package org.spring.divas.venue.feature.dish;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public record DishRequestDto(
        @NotNull Long venueId,
        @NotNull Long category,
        @NotBlank @Size(max = 150) String name,
        String description,
        @NotNull @DecimalMin(value = "0.00", inclusive = false) BigDecimal price,
        Set<Long> ingredients
) {
    public DishRequestDto {
        if (ingredients == null) {
            ingredients = new HashSet<>();
        }
    }
}
