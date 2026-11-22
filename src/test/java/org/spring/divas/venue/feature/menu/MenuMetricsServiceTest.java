package org.spring.divas.venue.feature.menu;

import static org.assertj.core.api.Assertions.assertThat;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.Test;

class MenuMetricsServiceTest {

    @Test
    void recordMenuRetrieval_shouldReturnSupplierResultAndRecordTimer() {
        SimpleMeterRegistry registry = new SimpleMeterRegistry();
        MenuMetricsService service = new MenuMetricsService(registry);
        String result = service.recordMenuRetrieval(() -> "menu");
        assertThat(result).isEqualTo("menu");
        assertThat(registry.find("venue.menu.retrieval").timer().count()).isEqualTo(1);
    }

    @Test
    void startAndStopPdfCreation_shouldFinishLongTask() {
        SimpleMeterRegistry registry = new SimpleMeterRegistry();
        MenuMetricsService service = new MenuMetricsService(registry);
        service.startPdfCreation();
        service.stopPdfGeneration();
        assertThat(registry.find("menu.pdf.generated").longTaskTimer().activeTasks()).isZero();
    }
}
