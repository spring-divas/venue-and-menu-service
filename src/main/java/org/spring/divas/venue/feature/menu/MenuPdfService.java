package org.spring.divas.venue.feature.menu;

import org.springframework.core.io.Resource;

public interface MenuPdfService {
    Resource getVenueMenuPdf(Long venueId);
    void createVenueMenuPdf(Long venueId);
}
