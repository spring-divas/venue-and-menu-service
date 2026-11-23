package org.spring.divas.venue.feature.menu;

import eu.rekawek.toxiproxy.model.ToxicDirection;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.spring.divas.venue.feature.AbstractToxiproxyIntegrationTest;
import org.spring.divas.venue.feature.category.Category;
import org.spring.divas.venue.feature.category.CategoryRepository;
import org.spring.divas.venue.feature.dish.Dish;
import org.spring.divas.venue.feature.dish.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MenuToxiproxyIntegrationTest extends AbstractToxiproxyIntegrationTest {

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void cleanDatabase() {
        dishRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void getVenueMenuReturnsMenuThroughPostgresProxy() throws Exception {
        Dish dish = saveDish();
        mockMvc.perform(get("/menu").param("venueId", "42"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(dish.getId()))
                .andExpect(jsonPath("$[0].name").value("Borscht"));
    }

    @Test
    void getVenueMenuReturnsServerErrorWhenPostgresResponseTimesOut() throws Exception {
        saveDish();
        POSTGRES_PROXY.toxics().timeout("postgres-response-timeout", ToxicDirection.DOWNSTREAM, 0);
        evictConnections();
        mockMvc.perform(get("/menu").param("venueId", "42"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.title").value("Internal Server Error"));
    }

    private Dish saveDish() {
        Category category = categoryRepository.save(Category.builder()
                .venueId(42L)
                .name("Soup")
                .build());
        return dishRepository.save(Dish.builder()
                .venueId(42L)
                .category(category)
                .name("Borscht")
                .description("Traditional")
                .price(BigDecimal.TEN)
                .build());
    }
}