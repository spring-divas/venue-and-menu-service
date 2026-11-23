package org.spring.divas.venue.feature.dish;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.spring.divas.venue.feature.category.CategoryResponseMapper;
import org.spring.divas.venue.feature.ingredient.IngredientResponseMapper;

@Mapper(
        componentModel = "spring",
        uses = {CategoryResponseMapper.class, IngredientResponseMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface DishResponseMapper {

    @Mapping(target = "ingredients", defaultExpression = "java(java.util.Set.of())")
    DishResponseDto toDto(Dish dish);
}
