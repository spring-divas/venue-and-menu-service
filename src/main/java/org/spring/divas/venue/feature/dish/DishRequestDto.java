package org.spring.divas.venue.feature.dish;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DishRequestDto {
    @NotNull
    private Long venueId;

    @NotNull
    private Long category;

    @NotBlank
    @Size(max = 150)
    private String name;
    private String description;

    @NotNull
    @DecimalMin(value = "0.00", inclusive = false)
    private BigDecimal price;

    @Builder.Default
    private Set<Long> ingredients = new HashSet<>();
}
