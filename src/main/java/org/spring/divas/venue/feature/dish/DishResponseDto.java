package org.spring.divas.venue.feature.dish;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring.divas.venue.feature.category.CategoryResponseDto;
import org.spring.divas.venue.feature.ingredient.IngredientResponseDto;

import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DishResponseDto {
    private Long id;
    private Long venueId;
    private CategoryResponseDto category;
    private String name;
    private String description;
    private BigDecimal price;
    private Float rating;
    private Integer reviewCount;
    private Set<IngredientResponseDto> ingredients;
}
