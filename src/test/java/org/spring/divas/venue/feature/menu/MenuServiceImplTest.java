package org.spring.divas.venue.feature.menu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.spring.divas.venue.feature.menu.MenuAssertions.assertMenu;
import static org.spring.divas.venue.feature.menu.MenuFixtures.MENU_VENUE_ID;
import static org.spring.divas.venue.feature.menu.MenuFixtures.menuDish;
import static org.spring.divas.venue.feature.menu.MenuFixtures.menuDishDto;

import java.util.List;
import java.util.function.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.spring.divas.venue.feature.dish.Dish;
import org.spring.divas.venue.feature.dish.DishRepository;
import org.spring.divas.venue.feature.dish.DishResponseDto;
import org.spring.divas.venue.feature.dish.DishResponseMapper;

@ExtendWith(MockitoExtension.class)
class MenuServiceImplTest {

    @Mock
    private MenuMetricsService metricsService;

    @Mock
    private DishRepository dishRepository;

    @Mock
    private DishResponseMapper dishResponseMapper;

    private MenuServiceImpl menuService;

    @BeforeEach
    void setUp() {
        menuService = new MenuServiceImpl(metricsService, dishRepository, dishResponseMapper);
        doAnswer(invocation -> ((Supplier<?>) invocation.getArgument(0)).get())
                .when(metricsService)
                .recordMenuRetrieval(any());
    }

    @Test
    void getVenueMenu_shouldMapDishesAndRecordMetric() {
        Dish dish = menuDish();
        when(dishRepository.findByVenueId(MENU_VENUE_ID)).thenReturn(List.of(dish));
        when(dishResponseMapper.toDto(dish)).thenReturn(menuDishDto());

        List<DishResponseDto> result = menuService.getVenueMenu(MENU_VENUE_ID);

        assertMenu(result);
        verify(metricsService).recordMenuRetrieval(any());
        verify(dishRepository).findByVenueId(MENU_VENUE_ID);
        verify(dishResponseMapper).toDto(dish);
    }

    @Test
    void getVenueMenu_shouldReturnEmptyListWhenVenueHasNoDishes() {
        when(dishRepository.findByVenueId(MENU_VENUE_ID)).thenReturn(List.of());

        List<DishResponseDto> result = menuService.getVenueMenu(MENU_VENUE_ID);

        assertThat(result).isEmpty();
        verify(metricsService).recordMenuRetrieval(any());
        verify(dishRepository).findByVenueId(MENU_VENUE_ID);
        verifyNoInteractions(dishResponseMapper);
    }
}
