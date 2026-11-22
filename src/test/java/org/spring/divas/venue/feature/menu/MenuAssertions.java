package org.spring.divas.venue.feature.menu;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import lombok.experimental.UtilityClass;
import org.spring.divas.venue.feature.dish.DishResponseDto;
import org.springframework.core.io.Resource;
import org.thymeleaf.context.Context;

@UtilityClass
public class MenuAssertions {

    public static void assertMenu(List<DishResponseDto> actual) {
        assertThat(actual).containsExactlyElementsOf(MenuFixtures.menu());
    }

    public static void assertPdfResource(Resource actual) {
        assertThat(actual.getFilename()).isEqualTo(MenuFixtures.PDF_PATH.getFileName().toString());
    }

    public static void assertHtml(String actual) {
        assertThat(actual).isEqualTo(MenuFixtures.html());
    }

    public static void assertMenuContext(Context actual) {
        @SuppressWarnings("unchecked")
        Map<String, List<DishResponseDto>> dishesByCategory =
                (Map<String, List<DishResponseDto>>) actual.getVariable("dishesByCategory");
        List<DishResponseDto> dishes = MenuFixtures.categorizedMenu();
        assertThat(actual.getVariable("title")).isEqualTo(MenuFixtures.title());
        assertThat(actual.getVariable("css")).isEqualTo(MenuFixtures.css());
        assertThat(dishesByCategory)
                .containsOnlyKeys("Starters", "Other")
                .containsEntry("Starters", List.of(dishes.getFirst(), dishes.getLast()))
                .containsEntry("Other", List.of(dishes.get(1)));
        assertThat(dishesByCategory.keySet()).containsExactly("Starters", "Other");
    }
}
