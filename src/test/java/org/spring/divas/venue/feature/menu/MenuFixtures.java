package org.spring.divas.venue.feature.menu;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import lombok.experimental.UtilityClass;
import org.spring.divas.venue.feature.category.CategoryResponseDto;
import org.spring.divas.venue.feature.dish.Dish;
import org.spring.divas.venue.feature.dish.DishResponseDto;
import org.spring.divas.venue.feature.venue.VenueResponseDto;
import org.springframework.core.io.ByteArrayResource;

@UtilityClass
public class MenuFixtures {

    public static final Long MENU_VENUE_ID = 7L;
    public static final Long PDF_VENUE_ID = 5L;
    public static final Path PDF_DIRECTORY = Path.of("menu-test-output");
    public static final Path PDF_PATH = PDF_DIRECTORY.resolve(PDF_VENUE_ID + ".pdf");

    public static Dish menuDish() {
        return Dish.builder().id(10L).venueId(MENU_VENUE_ID).name("Borscht").build();
    }

    public static DishResponseDto menuDishDto() {
        return new DishResponseDto(
                10L, MENU_VENUE_ID, null, "Borscht", "Traditional", BigDecimal.TEN, 4.5f, 3, null
        );
    }

    public static List<DishResponseDto> menu() {
        return List.of(menuDishDto());
    }

    public static VenueResponseDto venue() {
        return new VenueResponseDto(PDF_VENUE_ID, "Divas", "Kyiv", 4.0f, 2);
    }

    public static DishResponseDto pdfDish() {
        return new DishResponseDto(
                1L, PDF_VENUE_ID, null, "Varenyky", "Description", BigDecimal.TEN, 4.0f, 2, null
        );
    }

    public static List<DishResponseDto> pdfMenu() {
        return List.of(pdfDish());
    }

    public static List<DishResponseDto> emptyMenu() {
        return List.of();
    }

    public static String html() {
        return "<html>menu</html>";
    }

    public static String css() {
        return "body { color: black; }";
    }

    public static ByteArrayResource cssResource() {
        return new ByteArrayResource(loadedCss().getBytes(StandardCharsets.UTF_8));
    }

    public static String loadedCss() {
        return ".menu {}";
    }

    public static String title() {
        return "Cafe Divas";
    }

    public static List<DishResponseDto> categorizedMenu() {
        CategoryResponseDto starters = new CategoryResponseDto(1L, PDF_VENUE_ID, "Starters");
        return List.of(dish(1L, starters), dish(2L, null), dish(3L, starters));
    }

    private static DishResponseDto dish(Long id, CategoryResponseDto category) {
        return new DishResponseDto(
                id, PDF_VENUE_ID, category, "Dish " + id, "Description", BigDecimal.TEN, 0.0f, 0, null
        );
    }
}
