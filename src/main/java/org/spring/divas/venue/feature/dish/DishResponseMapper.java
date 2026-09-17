package org.spring.divas.venue.feature.dish;

import lombok.AllArgsConstructor;
import org.spring.divas.venue.feature.category.CategoryResponseMapper;
import org.spring.divas.venue.feature.ingredient.IngredientResponseMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class DishResponseMapper {
    private final CategoryResponseMapper categoryResponseMapper;
    private final IngredientResponseMapper ingredientResponseMapper;

    public DishResponseDto toDto(Dish dish) {
        return DishResponseDto.builder()
                .id(dish.getId())
                .venueId(dish.getVenueId())
                .category(categoryResponseMapper.toDto(dish.getCategory()))
                .name(dish.getName())
                .description(dish.getDescription())
                .price(dish.getPrice())
                .reviewCount(dish.getReviewCount())
                .ingredients(dish.getIngredients().stream().map(
                        ingredientResponseMapper::toDto
                ).collect(Collectors.toSet()))
                .build();
    }
}
