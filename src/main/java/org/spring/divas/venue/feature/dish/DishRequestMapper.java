package org.spring.divas.venue.feature.dish;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.spring.divas.venue.feature.category.Category;
import org.spring.divas.venue.feature.category.CategoryRepository;
import org.spring.divas.venue.feature.ingredient.Ingredient;
import org.spring.divas.venue.feature.ingredient.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class DishRequestMapper {
    private CategoryRepository categoryRepository;
    private IngredientRepository ingredientRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Autowired
    public void setIngredientRepository(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ingredients", defaultExpression = "java(new java.util.HashSet<>())")
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "reviewCount", ignore = true)
    public abstract Dish toEntity(DishRequestDto dto);

    protected Category toCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow();
    }

    protected Ingredient toIngredient(Long id) {
        return ingredientRepository.findById(id).orElseThrow();
    }
}
