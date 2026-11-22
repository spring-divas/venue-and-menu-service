package org.spring.divas.venue.feature;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Set;
import lombok.experimental.UtilityClass;
import org.spring.divas.dish.v1.DishChange;
import org.spring.divas.venue.feature.allergen.Allergen;
import org.spring.divas.venue.feature.allergen.AllergenResponseDto;
import org.spring.divas.venue.feature.category.Category;
import org.spring.divas.venue.feature.category.CategoryResponseDto;
import org.spring.divas.venue.feature.dish.Dish;
import org.spring.divas.venue.feature.dish.DishCreatedEvent;
import org.spring.divas.venue.feature.dish.DishResponseDto;
import org.spring.divas.venue.feature.dish.DishUpdatedEvent;
import org.spring.divas.venue.feature.ingredient.Ingredient;
import org.spring.divas.venue.feature.ingredient.IngredientResponseDto;
import org.spring.divas.venue.feature.outbox.OutboxEvent;
import org.spring.divas.venue.feature.venue.Venue;
import org.spring.divas.venue.feature.venue.VenueResponseDto;
import org.spring.divas.venue.feature.venuetable.VenueTable;
import org.spring.divas.venue.feature.venuetable.VenueTableResponseDto;

@UtilityClass
public class MapperAssertions {

    public static void assertAllergen(AllergenResponseDto actual) {
        assertThat(actual).isEqualTo(MapperFixtures.allergenDto());
    }

    public static void assertAllergen(Allergen actual) {
        assertThat(actual.getName()).isEqualTo(MapperFixtures.allergen().getName());
    }

    public static void assertCategory(CategoryResponseDto actual) {
        assertThat(actual).isEqualTo(MapperFixtures.categoryDto());
    }

    public static void assertCategory(Category actual) {
        assertThat(actual.getVenueId()).isEqualTo(MapperFixtures.category().getVenueId());
        assertThat(actual.getName()).isEqualTo(MapperFixtures.category().getName());
    }

    public static void assertIngredient(IngredientResponseDto actual, Set<AllergenResponseDto> allergens) {
        assertThat(actual).isEqualTo(MapperFixtures.ingredientDto(allergens));
    }

    public static void assertIngredient(Ingredient actual, Set<Allergen> allergens) {
        assertThat(actual.getName()).isEqualTo(MapperFixtures.ingredient().getName());
        assertThat(actual.getQuantity()).isEqualTo(MapperFixtures.ingredient().getQuantity());
        assertThat(actual.getAllergens()).isEqualTo(allergens);
    }

    public static void assertDish(
            DishResponseDto actual,
            CategoryResponseDto category,
            Set<IngredientResponseDto> ingredients
    ) {
        assertThat(actual).isEqualTo(MapperFixtures.dishDto(category, ingredients));
    }

    public static void assertDish(Dish actual, Category category, Set<Ingredient> ingredients) {
        Dish expected = MapperFixtures.dish();
        assertThat(actual.getVenueId()).isEqualTo(expected.getVenueId());
        assertThat(actual.getCategory()).isEqualTo(category);
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
        assertThat(actual.getPrice()).isEqualByComparingTo(expected.getPrice());
        assertThat(actual.getIngredients()).isEqualTo(ingredients);
    }

    public static void assertVenue(VenueResponseDto actual) {
        assertThat(actual).isEqualTo(MapperFixtures.venueDto());
    }

    public static void assertVenue(Venue actual) {
        assertThat(actual.getName()).isEqualTo(MapperFixtures.venue().getName());
        assertThat(actual.getAddress()).isEqualTo(MapperFixtures.venue().getAddress());
    }

    public static void assertVenueTable(VenueTableResponseDto actual) {
        assertThat(actual).isEqualTo(MapperFixtures.venueTableDto());
    }

    public static void assertVenueTable(VenueTable actual) {
        assertThat(actual.getVenueId()).isEqualTo(MapperFixtures.venueTableRequest().venueId());
        assertThat(actual.getIsAvailable()).isEqualTo(MapperFixtures.venueTableRequest().isAvailable());
    }

    public static void assertDishChange(DishChange actual) {
        BigDecimal price = new BigDecimal(
                new BigInteger(actual.getPrice().getValue().toByteArray()),
                actual.getPrice().getScale()
        );
        Dish expected = MapperFixtures.protoDish();
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getPrice().getScale()).isEqualTo(expected.getPrice().scale());
        assertThat(actual.getPrice().getPrecision()).isEqualTo(expected.getPrice().precision());
        assertThat(price).isEqualByComparingTo(expected.getPrice());
    }

    public static void assertUpdatedEvent(DishUpdatedEvent actual) {
        assertThat(actual.eventId()).isNotNull();
        assertThat(actual.dishId()).isEqualTo(MapperFixtures.dish().getId());
        assertThat(actual.venueId()).isEqualTo(MapperFixtures.dish().getVenueId());
    }

    public static void assertCreatedEvent(DishCreatedEvent actual) {
        assertThat(actual.eventId()).isNotNull();
        assertThat(actual.dishId()).isEqualTo(MapperFixtures.dish().getId());
        assertThat(actual.venueId()).isEqualTo(MapperFixtures.dish().getVenueId());
    }

    public static void assertUpdatedOutbox(OutboxEvent actual, DishUpdatedEvent event) {
        assertOutbox(actual, event.eventId(), MapperFixtures.updatedEventType(), MapperFixtures.updatedPayload());
    }

    public static void assertCreatedOutbox(OutboxEvent actual, DishCreatedEvent event) {
        assertOutbox(actual, event.eventId(), MapperFixtures.createdEventType(), MapperFixtures.createdPayload());
    }

    private static void assertOutbox(OutboxEvent actual, java.util.UUID eventId, String eventType, String payload) {
        assertThat(actual.getId()).isEqualTo(eventId);
        assertThat(actual.getAggregateId()).isEqualTo(String.valueOf(MapperFixtures.dish().getId()));
        assertThat(actual.getEventType()).isEqualTo(eventType);
        assertThat(actual.getPayload()).isEqualTo(payload);
    }
}
