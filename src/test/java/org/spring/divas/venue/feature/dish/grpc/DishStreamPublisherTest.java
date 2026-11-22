package org.spring.divas.venue.feature.dish.grpc;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.times;

import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Test;
import org.spring.divas.dish.v1.DishChange;

class DishStreamPublisherTest {

    @Test
    void publish_shouldSendChangeToSubscribedObserver() {
        DishStreamPublisher publisher = new DishStreamPublisher();
        StreamObserver<DishChange> observer = observer();
        DishChange change = DishChange.getDefaultInstance();
        publisher.subscribe(observer);

        publisher.publish(change);

        verify(observer).onNext(change);
    }

    @Test
    void unsubscribe_shouldPreventFurtherNotifications() {
        DishStreamPublisher publisher = new DishStreamPublisher();
        StreamObserver<DishChange> observer = observer();
        publisher.subscribe(observer);
        publisher.unsubscribe(observer);

        publisher.publish(DishChange.getDefaultInstance());

        verifyNoInteractions(observer);
    }

    @Test
    void publish_shouldRemoveObserverWhenItThrows() {
        DishStreamPublisher publisher = new DishStreamPublisher();
        StreamObserver<DishChange> observer = observer();
        DishChange change = DishChange.getDefaultInstance();
        doThrow(new RuntimeException("stream closed")).when(observer).onNext(change);
        publisher.subscribe(observer);

        publisher.publish(change);
        publisher.publish(change);

        verify(observer, times(1)).onNext(change);
    }

    @SuppressWarnings("unchecked")
    private StreamObserver<DishChange> observer() {
        return mock(StreamObserver.class);
    }
}
