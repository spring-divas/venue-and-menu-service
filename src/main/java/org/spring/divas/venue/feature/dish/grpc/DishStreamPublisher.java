package org.spring.divas.venue.feature.dish.grpc;

import io.grpc.stub.StreamObserver;
import org.spring.divas.dish.v1.DishChange;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ConditionalOnProperty(name = "dish.sync.transport", havingValue = "grpc")
public class DishStreamPublisher {
    private final Set<StreamObserver<DishChange>> observers = ConcurrentHashMap.newKeySet();

    public void subscribe(StreamObserver<DishChange> observer) {
        observers.add(observer);
    }

    public void unsubscribe(StreamObserver<DishChange> observer) {
        observers.remove(observer);
    }

    public void publish(DishChange change) {
        observers.removeIf(observer -> !send(observer, change));
    }

    private boolean send(StreamObserver<DishChange> observer, DishChange change) {
        try {
            observer.onNext(change);
            return true;
        } catch (RuntimeException _) {
            return false;
        }
    }
}
