package org.spring.divas.venue.feature.dish;

import lombok.AllArgsConstructor;
import org.spring.divas.venue.feature.category.CategoryRequestMapper;
import org.spring.divas.venue.feature.ingredient.IngredientRequestMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class DishRequestMapper {
    private final CategoryRequestMapper categoryRequestMapper;
    private final IngredientRequestMapper ingredientRequestMapper;

    public Dish toEntity(DishRequestDto dto) {
        return Dish.builder()
                .venueId(dto.getVenueId())
                .category(categoryRequestMapper.toEntity(dto.getCategory()))
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .rating(dto.getRating())
                .reviewCount(dto.getReviewCount())
                .ingredients(dto.getIngredients().stream().map(
                        ingredientRequestMapper::toEntity
                ).collect(Collectors.toSet()))
                .build();
    }
}
