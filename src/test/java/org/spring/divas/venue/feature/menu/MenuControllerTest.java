package org.spring.divas.venue.feature.menu;

import static org.spring.divas.venue.feature.ControllerAssertions.assertResult;
import static org.spring.divas.venue.feature.ControllerFixtures.menuPdf;
import static org.spring.divas.venue.feature.menu.MenuFixtures.PDF_VENUE_ID;
import static org.spring.divas.venue.feature.menu.MenuFixtures.menu;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.spring.divas.venue.feature.dish.DishResponseDto;
import org.springframework.core.io.Resource;

@ExtendWith(MockitoExtension.class)
class MenuControllerTest {

    @Mock
    private MenuService menuService;

    @Mock
    private MenuPdfService menuPdfService;

    @InjectMocks
    private MenuController controller;

    @Test
    void getVenueMenu_shouldDelegateToService() {
        List<DishResponseDto> response = menu();
        when(menuService.getVenueMenu(PDF_VENUE_ID)).thenReturn(response);
        assertResult(controller.getVenueMenu(PDF_VENUE_ID), response);
        verify(menuService).getVenueMenu(PDF_VENUE_ID);
    }

    @Test
    void getVenueMenuPdf_shouldDelegateToService() {
        Resource response = menuPdf();
        when(menuPdfService.getVenueMenuPdf(PDF_VENUE_ID)).thenReturn(response);
        assertResult(controller.getVenueMenuPdf(PDF_VENUE_ID), response);
        verify(menuPdfService).getVenueMenuPdf(PDF_VENUE_ID);
    }
}
