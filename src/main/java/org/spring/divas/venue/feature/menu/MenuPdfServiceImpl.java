package org.spring.divas.venue.feature.menu;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.openpdf.text.pdf.BaseFont;
import org.spring.divas.venue.feature.dish.DishResponseDto;
import org.spring.divas.venue.feature.venue.VenueResponseDto;
import org.spring.divas.venue.feature.venue.VenueService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuPdfServiceImpl implements MenuPdfService {
    private final MenuHtmlServiceImpl menuHtmlServiceImpl;
    private final MenuService menuService;
    private final VenueService venueService;

    @Value("${menu.pdf.dir}")
    private Path pdfDir;

    @Value("${menu.fonts.path}")
    private Resource fontResource;

    private Path fontPath;

    @PostConstruct
    public void init() {
        fontPath = loadFont();
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
        String html = generateMenuHtml(venueId);
        Path targetPath = pdfPath(venueId);
        try (ByteArrayOutputStream memoryOut = new ByteArrayOutputStream()) {
            render(html, memoryOut);
            Files.createDirectories(pdfDir);
            Files.write(targetPath, memoryOut.toByteArray());
        } catch (IOException e) {
            throw new UncheckedIOException("Error saving PDF to disk", e);
        }
    }

    private String generateMenuHtml(Long venueId) {
        VenueResponseDto venue = venueService.getById(venueId);
        List<DishResponseDto> menu = menuService.getVenueMenu(venueId);
        return menuHtmlServiceImpl.render(venue.getName(), menu);
    }

    public void render(String html, OutputStream out) {
        try {
            ITextRenderer renderer = new ITextRenderer();
            renderer.getFontResolver().addFont(
                    fontPath.toString(),
                    BaseFont.IDENTITY_H,
                    BaseFont.EMBEDDED);
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(out);
        } catch (Exception ignored) {}
    }

    private Path loadFont() {
        try (InputStream in = fontResource.getInputStream()) {
            Path font = Files.createTempFile("menu-font-", ".ttf");
            font.toFile().deleteOnExit();
            Files.copy(in, font, StandardCopyOption.REPLACE_EXISTING);
            return font;
        } catch (IOException e) {
            throw new UncheckedIOException("Font couldn't be loaded", e);
        }
    }

    private Path pdfPath(Long venueId) {
        return pdfDir.resolve(venueId + ".pdf");
    }
}
