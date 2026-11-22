package org.spring.divas.venue.feature;

import java.util.List;
import lombok.experimental.UtilityClass;
import org.spring.divas.venue.feature.allergen.AllergenResponseDto;
import org.spring.divas.venue.feature.category.CategoryResponseDto;
import org.spring.divas.venue.feature.dish.DishResponseDto;
import org.spring.divas.venue.feature.ingredient.IngredientResponseDto;
import org.spring.divas.venue.feature.venue.VenueResponseDto;
import org.spring.divas.venue.feature.venuetable.VenueTableResponseDto;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

@UtilityClass
public class ControllerFixtures {

    public static List<AllergenResponseDto> allergens() {
        return List.of(MapperFixtures.allergenDto());
    }

    public static List<CategoryResponseDto> categories() {
        return List.of(MapperFixtures.categoryDto());
    }

    public static List<IngredientResponseDto> ingredients() {
        return List.of(MapperFixtures.ingredientDto());
    }

    public static List<DishResponseDto> dishes() {
        return List.of(MapperFixtures.dishDto());
    }

    public static List<Long> dishIds() {
        return List.of(MapperFixtures.dish().getId());
    }

    public static List<VenueResponseDto> venues() {
        return List.of(MapperFixtures.venueDto());
    }

    public static List<VenueTableResponseDto> venueTables() {
        return List.of(MapperFixtures.venueTableDto());
    }

    public static Resource menuPdf() {
        return new ByteArrayResource("%PDF".getBytes());
    }
}
