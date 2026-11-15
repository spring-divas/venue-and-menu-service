package org.spring.divas.venue.feature.dish.grpc;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@ConditionalOnProperty(name = "dish.sync.transport", havingValue = "grpc")
@RequiredArgsConstructor
public class DishStreamEventListener {
    private final DishStreamPublisher dishStreamPublisher;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publish(DishChangeEvent event) {
        dishStreamPublisher.publish(event.change());
    }
}
