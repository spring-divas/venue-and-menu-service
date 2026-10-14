package org.spring.divas.venue.feature.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class MenuChangedListener {
    private final MenuPdfServiceImpl menuPdfServiceImpl;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleMenuChanged(MenuChangedEvent event) {
        menuPdfServiceImpl.createVenueMenuPdf(event.venueId());
    }
}
