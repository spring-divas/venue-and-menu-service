package org.spring.divas.venue.feature.menu;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.spring.divas.venue.feature.menu.MenuAssertions.assertHtml;
import static org.spring.divas.venue.feature.menu.MenuAssertions.assertPdfResource;
import static org.spring.divas.venue.feature.menu.MenuFixtures.PDF_DIRECTORY;
import static org.spring.divas.venue.feature.menu.MenuFixtures.PDF_PATH;
import static org.spring.divas.venue.feature.menu.MenuFixtures.PDF_VENUE_ID;
import static org.spring.divas.venue.feature.menu.MenuFixtures.html;
import static org.spring.divas.venue.feature.menu.MenuFixtures.emptyMenu;
import static org.spring.divas.venue.feature.menu.MenuFixtures.pdfMenu;
import static org.spring.divas.venue.feature.menu.MenuFixtures.venue;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.spring.divas.venue.feature.venue.VenueService;
import org.springframework.core.io.Resource;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class MenuPdfServiceImplTest {

    @Mock
    private MenuHtmlServiceImpl menuHtmlService;

    @Mock
    private MenuService menuService;

    @Mock
    private VenueService venueService;

    @Mock
    private MenuMetricsService metricsService;

    private MenuPdfServiceImpl menuPdfService;

    @BeforeEach
    void setUp() {
        menuPdfService = new MenuPdfServiceImpl(menuHtmlService, menuService, venueService, metricsService);
        ReflectionTestUtils.setField(menuPdfService, "pdfDir", PDF_DIRECTORY);
    }

    @Test
    void getVenueMenuPdf_shouldReturnExistingPdfWithoutRegeneratingIt() {
        try (MockedStatic<Files> files = mockStatic(Files.class)) {
            files.when(() -> Files.exists(PDF_PATH)).thenReturn(true);
            Resource result = menuPdfService.getVenueMenuPdf(PDF_VENUE_ID);
            assertPdfResource(result);
            verifyNoInteractions(metricsService);
        }
    }

    @Test
    void getVenueMenuPdf_shouldGeneratePdfWhenItDoesNotExist() {
        MenuPdfServiceImpl service = spy(menuPdfService);
        doNothing().when(service).createVenueMenuPdf(PDF_VENUE_ID);
        try (MockedStatic<Files> files = mockStatic(Files.class)) {
            files.when(() -> Files.exists(PDF_PATH)).thenReturn(false);
            Resource result = service.getVenueMenuPdf(PDF_VENUE_ID);
            assertPdfResource(result);
            verify(service).createVenueMenuPdf(PDF_VENUE_ID);
        }
    }

    @Test
    void createVenueMenuPdf_shouldRenderAndSavePdfWithoutWritingToDisk() {
        MenuPdfServiceImpl service = spy(menuPdfService);
        when(venueService.getById(PDF_VENUE_ID)).thenReturn(venue());
        when(menuService.getVenueMenu(PDF_VENUE_ID)).thenReturn(pdfMenu());
        when(menuHtmlService.render(venue().name(), pdfMenu())).thenReturn(html());
        doAnswer(invocation -> {
            assertHtml(invocation.getArgument(0, String.class));
            return null;
        }).when(service).render(anyString(), any(OutputStream.class));
        try (MockedStatic<Files> files = mockStatic(Files.class)) {
            files.when(() -> Files.createDirectories(PDF_DIRECTORY)).thenReturn(PDF_DIRECTORY);
            files.when(() -> Files.write(any(Path.class), any(byte[].class))).thenReturn(PDF_PATH);
            service.createVenueMenuPdf(PDF_VENUE_ID);
            files.verify(() -> Files.createDirectories(PDF_DIRECTORY));
            files.verify(() -> Files.write(eq(PDF_PATH), any(byte[].class)));
            verify(metricsService).startPdfCreation();
            verify(metricsService).stopPdfGeneration();
        }
    }

    @Test
    void createVenueMenuPdf_shouldWrapIoExceptionAndStopMetric() {
        MenuPdfServiceImpl service = spy(menuPdfService);
        setUpHtmlGeneration();
        doNothing().when(service).render(anyString(), any(OutputStream.class));
        try (MockedStatic<Files> files = mockStatic(Files.class)) {
            files.when(() -> Files.createDirectories(PDF_DIRECTORY))
                    .thenThrow(new IOException("Directory is unavailable"));
            assertThatThrownBy(() -> service.createVenueMenuPdf(PDF_VENUE_ID))
                    .isInstanceOf(java.io.UncheckedIOException.class)
                    .hasMessage("Error saving PDF to disk")
                    .hasCauseInstanceOf(IOException.class);
            verify(metricsService).startPdfCreation();
            verify(metricsService).stopPdfGeneration();
        }
    }

    @Test
    void loadFont_shouldWrapResourceReadFailure() throws IOException {
        Resource fontResource = mock(Resource.class);
        when(fontResource.getContentAsByteArray()).thenThrow(new IOException("Font is unavailable"));
        ReflectionTestUtils.setField(menuPdfService, "fontResource", fontResource);
        assertThatThrownBy(menuPdfService::loadFont)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Font couldn't be loaded")
                .hasCauseInstanceOf(IOException.class);
    }

    private void setUpHtmlGeneration() {
        when(venueService.getById(PDF_VENUE_ID)).thenReturn(venue());
        when(menuService.getVenueMenu(PDF_VENUE_ID)).thenReturn(emptyMenu());
        when(menuHtmlService.render(venue().name(), emptyMenu())).thenReturn(html());
    }
}
