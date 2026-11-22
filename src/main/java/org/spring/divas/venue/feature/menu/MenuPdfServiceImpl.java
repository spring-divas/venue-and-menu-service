package org.spring.divas.venue.feature.menu;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.openpdf.text.DocumentException;
import org.openpdf.text.pdf.BaseFont;
import org.spring.divas.venue.feature.dish.DishResponseDto;
import org.spring.divas.venue.feature.venue.VenueResponseDto;
import org.spring.divas.venue.feature.venue.VenueService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuPdfServiceImpl implements MenuPdfService {
    private final MenuHtmlServiceImpl menuHtmlServiceImpl;
    private final MenuService menuService;
    private final VenueService venueService;
    private final MenuMetricsService menuMetricsService;

    @Value("${menu.pdf.dir}")
    private Path pdfDir;

    @Value("${menu.fonts.path}")
    private Resource fontResource;

    private BaseFont font;

    @PostConstruct
    void loadFont() {
        try {
            font = BaseFont.createFont(
                    "NotoSans.ttf",
                    BaseFont.IDENTITY_H,
                    BaseFont.EMBEDDED,
                    false,
                    fontResource.getContentAsByteArray(),
                    null
            );
        } catch (DocumentException | IOException e) {
            throw new IllegalStateException("Font couldn't be loaded", e);
        }
    }

    @Override
    public Resource getVenueMenuPdf(Long venueId) {
        Path pdf = pdfPath(venueId);
        if (!Files.exists(pdf)) {
            createVenueMenuPdf(venueId);
        }
        return new FileSystemResource(pdf);
    }

    @Override
    public void createVenueMenuPdf(Long venueId) {
        menuMetricsService.startPdfCreation();
        String html = generateMenuHtml(venueId);
        Path targetPath = pdfPath(venueId);
        try (ByteArrayOutputStream memoryOut = new ByteArrayOutputStream()) {
            render(html, memoryOut);
            Files.createDirectories(pdfDir);
            Files.write(targetPath, memoryOut.toByteArray());
        } catch (IOException e) {
            throw new UncheckedIOException("Error saving PDF to disk", e);
        } finally {
            menuMetricsService.stopPdfGeneration();
        }
    }

    private String generateMenuHtml(Long venueId) {
        VenueResponseDto venue = venueService.getById(venueId);
        List<DishResponseDto> menu = menuService.getVenueMenu(venueId);
        return menuHtmlServiceImpl.render(venue.name(), menu);
    }

    public void render(String html, OutputStream out) {
        ITextRenderer renderer = new ITextRenderer();
        renderer.getFontResolver().addFont(font, "Noto Sans", BaseFont.IDENTITY_H);
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(out);
    }

    private Path pdfPath(Long venueId) {
        return pdfDir.resolve(venueId + ".pdf");
    }
}
