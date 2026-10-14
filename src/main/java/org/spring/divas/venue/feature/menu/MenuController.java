package org.spring.divas.venue.feature.menu;

import lombok.AllArgsConstructor;
import org.spring.divas.venue.feature.dish.DishResponseDto;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
@AllArgsConstructor
public class MenuController {
    private final MenuService menuService;
    private final MenuPdfService menuPdfService;

    @GetMapping
    public List<DishResponseDto> getVenueMenu(@RequestParam Long venueId) {
        return menuService.getVenueMenu(venueId);
    }

    @GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public Resource getVenueMenuPdf(@RequestParam Long venueId) {
        return menuPdfService.getVenueMenuPdf(venueId);
    }
}
