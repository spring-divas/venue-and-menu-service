package org.spring.divas.venue;

import lombok.NoArgsConstructor;
import org.spring.divas.venue.feature.category.Category;
import org.spring.divas.venue.feature.dish.Dish;
import org.spring.divas.venue.feature.dish.DishRequestDto;

import java.math.BigDecimal;
import java.util.Set;

@NoArgsConstructor
final class DishFixtures {
    private static final Long VENUE_ID = 1L;

    static Category category() {
        return Category.builder()
                .name("Soups")
                .venueId(VENUE_ID)
                .build();
    }

    static DishRequestDto request(Long categoryId) {
        return new DishRequestDto(
                VENUE_ID,
                categoryId,
                "Borscht",
                "Beet soup",
                BigDecimal.valueOf(120),
                Set.of()
        );
    }

    static Dish dish() {
        return Dish.builder()
                .id(42L)
                .venueId(VENUE_ID)
                .name("Soup")
                .price(BigDecimal.TEN)
                .build();
    }
}