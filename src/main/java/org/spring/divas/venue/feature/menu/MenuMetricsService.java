package org.spring.divas.venue.feature.menu;

import io.micrometer.core.instrument.LongTaskTimer;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class MenuMetricsService {
    private final LongTaskTimer pdfCreation;
    private LongTaskTimer.Sample sample;

    public MenuMetricsService(MeterRegistry registry) {
        this.pdfCreation = LongTaskTimer.builder("menu.pdf.generated")
                .description("Active menu PDF generation tasks")
                .register(registry);
    }

    public void startPdfCreation() {
        sample = pdfCreation.start();
    }

    public void stopPdfGeneration() {
        sample.stop();
    }
}
