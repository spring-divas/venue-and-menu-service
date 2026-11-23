package org.spring.divas.venue.feature.ingredient;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.spring.divas.venue.feature.allergen.AllergenResponseMapper;

@Mapper(
        componentModel = "spring",
        uses = AllergenResponseMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface IngredientResponseMapper {

    @Mapping(target = "allergens", defaultExpression = "java(java.util.Set.of())")
    IngredientResponseDto toDto(Ingredient ingredient);
}
