package org.spring.divas.venue.common.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResourceNotFoundMessages {
    public static final String ALLERGEN_NOT_FOUND = "Allergen not found.";
    public static final String CATEGORY_NOT_FOUND = "Category not found.";
    public static final String DISH_NOT_FOUND = "Dish not found.";
    public static final String INGREDIENT_NOT_FOUND = "Ingredient not found.";
    public static final String INGREDIENTS_NOT_FOUND = "One or more ingredients not found";
    public static final String ALLERGENS_NOT_FOUND = "One or more allergens not found";
    public static final String TABLE_NOT_FOUND = "Table not found.";
    public static final String VENUE_NOT_FOUND = "Venue not found.";
}
