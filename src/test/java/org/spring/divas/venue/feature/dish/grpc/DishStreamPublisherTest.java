package org.spring.divas.venue.feature.dish.grpc;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.times;
import static org.spring.divas.venue.feature.dish.grpc.DishGrpcFixtures.change;
import static org.spring.divas.venue.feature.dish.grpc.DishGrpcFixtures.closedStreamException;

import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.spring.divas.dish.v1.DishChange;

@ExtendWith(MockitoExtension.class)
class DishStreamPublisherTest {

    @Mock
    private StreamObserver<DishChange> observer;

    @Test
    void publish_shouldSendChangeToSubscribedObserver() {
        DishStreamPublisher publisher = new DishStreamPublisher();
        DishChange change = change();
        publisher.subscribe(observer);

        publisher.publish(change);

        verify(observer).onNext(change);
    }

    @Test
    void unsubscribe_shouldPreventFurtherNotifications() {
        DishStreamPublisher publisher = new DishStreamPublisher();
        publisher.subscribe(observer);
        publisher.unsubscribe(observer);

        publisher.publish(change());

        verifyNoInteractions(observer);
    }

    @Test
    void publish_shouldRemoveObserverWhenItThrows() {
        DishStreamPublisher publisher = new DishStreamPublisher();
        DishChange change = change();
        doThrow(closedStreamException()).when(observer).onNext(change);
        publisher.subscribe(observer);

        publisher.publish(change);
        publisher.publish(change);

        verify(observer, times(1)).onNext(change);
    }
}
