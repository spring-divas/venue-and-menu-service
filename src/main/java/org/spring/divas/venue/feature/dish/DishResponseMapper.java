package org.spring.divas.venue.feature.dish;

import lombok.AllArgsConstructor;
import org.spring.divas.venue.feature.category.CategoryResponseMapper;
import org.spring.divas.venue.feature.ingredient.IngredientResponseMapper;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class DishResponseMapper {
    private final CategoryResponseMapper categoryResponseMapper;
    private final IngredientResponseMapper ingredientResponseMapper;

    public DishResponseDto toDto(Dish dish) {
        return new DishResponseDto(
                dish.getId(),
                dish.getVenueId(),
                categoryResponseMapper.toDto(dish.getCategory()),
                dish.getName(),
                dish.getDescription(),
                dish.getPrice(),
                dish.getRating(),
                dish.getReviewCount(),
                dish.getIngredients() != null
                        ? dish.getIngredients().stream().map(ingredientResponseMapper::toDto).collect(Collectors.toSet())
                        : Set.of()
        );
    }
}
