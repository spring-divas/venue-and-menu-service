package org.spring.divas.venue.feature;

import java.math.BigDecimal;
import java.util.Set;
import lombok.experimental.UtilityClass;
import org.spring.divas.venue.feature.allergen.Allergen;
import org.spring.divas.venue.feature.allergen.AllergenRequestDto;
import org.spring.divas.venue.feature.allergen.AllergenResponseDto;
import org.spring.divas.venue.feature.category.Category;
import org.spring.divas.venue.feature.category.CategoryRequestDto;
import org.spring.divas.venue.feature.category.CategoryResponseDto;
import org.spring.divas.venue.feature.dish.Dish;
import org.spring.divas.venue.feature.dish.DishCreatedEvent;
import org.spring.divas.venue.feature.dish.DishRequestDto;
import org.spring.divas.venue.feature.dish.DishResponseDto;
import org.spring.divas.venue.feature.dish.DishUpdatedEvent;
import org.spring.divas.venue.feature.ingredient.Ingredient;
import org.spring.divas.venue.feature.ingredient.IngredientRequestDto;
import org.spring.divas.venue.feature.ingredient.IngredientResponseDto;
import org.spring.divas.venue.feature.venue.Venue;
import org.spring.divas.venue.feature.venue.VenueRequestDto;
import org.spring.divas.venue.feature.venue.VenueResponseDto;
import org.spring.divas.venue.feature.venuetable.VenueTable;
import org.spring.divas.venue.feature.venuetable.VenueTableRequestDto;
import org.spring.divas.venue.feature.venuetable.VenueTableResponseDto;
import java.util.UUID;

@UtilityClass
public class MapperFixtures {

    public static Allergen allergen() {
        return Allergen.builder().id(2L).name("Gluten").build();
    }

    public static AllergenResponseDto allergenDto() {
        return new AllergenResponseDto(2L, "Gluten");
    }

    public static AllergenRequestDto allergenRequest() {
        return new AllergenRequestDto("Gluten");
    }

    public static Category category() {
        return Category.builder().id(2L).venueId(5L).name("Main").build();
    }

    public static CategoryResponseDto categoryDto() {
        return new CategoryResponseDto(2L, 5L, "Main");
    }

    public static CategoryRequestDto categoryRequest() {
        return new CategoryRequestDto(5L, "Main");
    }

    public static Ingredient ingredient() {
        return Ingredient.builder().id(3L).name("Cheese").quantity(1).build();
    }

    public static IngredientResponseDto ingredientDto() {
        return ingredientDto(Set.of());
    }

    public static IngredientResponseDto ingredientDto(Set<AllergenResponseDto> allergens) {
        return new IngredientResponseDto(3L, "Cheese", 1, allergens);
    }

    public static IngredientRequestDto ingredientRequest() {
        return new IngredientRequestDto("Cheese", 1, Set.of(allergen().getId()));
    }

    public static IngredientRequestDto ingredientRequestWithoutAllergens() {
        return new IngredientRequestDto("Water", 1, null);
    }

    public static Dish dish() {
        return Dish.builder()
                .id(1L)
                .venueId(5L)
                .category(category())
                .name("Pasta")
                .description("Fresh")
                .price(BigDecimal.TEN)
                .rating(4.5f)
                .reviewCount(7)
                .ingredients(Set.of(ingredient()))
                .build();
    }

    public static Dish protoDish() {
        return Dish.builder().id(4L).name("Borscht").price(new BigDecimal("123.45")).build();
    }

    public static DishResponseDto dishDto() {
        return dishDto(categoryDto(), Set.of(ingredientDto()));
    }

    public static DishResponseDto dishDto(CategoryResponseDto category, Set<IngredientResponseDto> ingredients) {
        return new DishResponseDto(1L, 5L, category, "Pasta", "Fresh", BigDecimal.TEN, 4.5f, 7, ingredients);
    }

    public static DishRequestDto dishRequest() {
        return new DishRequestDto(5L, category().getId(), "Pasta", "Fresh", BigDecimal.TEN, Set.of(ingredient().getId()));
    }

    public static DishRequestDto dishRequestWithMissingCategory() {
        return new DishRequestDto(5L, category().getId(), "Pasta", "Fresh", BigDecimal.TEN, null);
    }

    public static DishUpdatedEvent updatedEvent() {
        return new DishUpdatedEvent(UUID.fromString("00000000-0000-0000-0000-000000000001"), 1L, 5L, "Pasta", BigDecimal.TEN);
    }

    public static String updatedPayload() {
        return "updated-payload";
    }

    public static String updatedEventType() {
        return "DishUpdated";
    }

    public static DishCreatedEvent createdEvent() {
        return new DishCreatedEvent(UUID.fromString("00000000-0000-0000-0000-000000000002"), 1L, 5L, "Pasta", BigDecimal.TEN);
    }

    public static String createdPayload() {
        return "created-payload";
    }

    public static String createdEventType() {
        return "DishCreated";
    }

    public static Venue venue() {
        return Venue.builder()
                .id(3L)
                .name("Divas")
                .address("Kyiv")
                .rating(4.5f)
                .reviewCount(12)
                .build();
    }

    public static VenueResponseDto venueDto() {
        return new VenueResponseDto(3L, "Divas", "Kyiv", 4.5f, 12);
    }

    public static VenueRequestDto venueRequest() {
        return new VenueRequestDto("Divas", "Kyiv");
    }

    public static VenueTable venueTable() {
        return VenueTable.builder().id(9L).venueId(3L).isAvailable(false).build();
    }

    public static VenueTableResponseDto venueTableDto() {
        return new VenueTableResponseDto(9L, 3L, false);
    }

    public static VenueTableRequestDto venueTableRequest() {
        return new VenueTableRequestDto(3L, true);
    }
}
