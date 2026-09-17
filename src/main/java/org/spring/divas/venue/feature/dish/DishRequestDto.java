package org.spring.divas.venue.feature.dish;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring.divas.venue.feature.category.CategoryRequestDto;
import org.spring.divas.venue.feature.ingredient.IngredientRequestDto;

import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DishRequestDto {
    private Long venueId;
    private CategoryRequestDto category;

    @NotBlank
    @Size(max = 150)
    private String name;

    private String description;
    private BigDecimal price;

    @PositiveOrZero
    private Float rating;

    @PositiveOrZero
    private Integer reviewCount;

    @NotEmpty
    private Set<IngredientRequestDto> ingredients;
}
