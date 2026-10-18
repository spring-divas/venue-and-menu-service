package org.spring.divas.venue.feature.menu;

import io.micrometer.core.instrument.LongTaskTimer;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import java.util.function.Supplier;
import org.springframework.stereotype.Service;

@Service
public class MenuMetricsService {
    private final LongTaskTimer pdfCreation;
    private LongTaskTimer.Sample sample;
    private final Timer venueMenuRetrieval;

    public MenuMetricsService(MeterRegistry registry) {
        this.pdfCreation = LongTaskTimer.builder("menu.pdf.generated")
                .description("Active menu PDF generation tasks")
                .register(registry);
        this.venueMenuRetrieval = Timer.builder("venue.menu.retrieval")
                .description("Venue's menu retrieval")
                .register(registry);
    }

    public void startPdfCreation() {
        sample = pdfCreation.start();
    }

    public void stopPdfGeneration() {
        sample.stop();
    }

    public <T> T recordMenuRetrieval(Supplier<T> action) {
        return venueMenuRetrieval.record(action);
    }
}
