package org.spring.divas.venue.feature.ingredient;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.spring.divas.venue.feature.allergen.Allergen;
import org.spring.divas.venue.feature.allergen.AllergenRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class IngredientRequestMapper {
    private AllergenRepository allergenRepository;

    @Autowired
    public void setAllergenRepository(AllergenRepository allergenRepository) {
        this.allergenRepository = allergenRepository;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "allergens", defaultExpression = "java(new java.util.HashSet<>())")
    public abstract Ingredient toEntity(IngredientRequestDto dto);

    protected Allergen toAllergen(Long id) {
        return allergenRepository.findById(id).orElseThrow();
    }
}
