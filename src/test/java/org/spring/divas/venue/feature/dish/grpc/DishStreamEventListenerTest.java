package org.spring.divas.venue.feature.dish.grpc;

import static org.mockito.Mockito.verify;
import static org.spring.divas.venue.feature.dish.grpc.DishGrpcFixtures.change;
import static org.spring.divas.venue.feature.dish.grpc.DishGrpcFixtures.changeEvent;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DishStreamEventListenerTest {

    @Mock
    private DishStreamPublisher publisher;

    @InjectMocks
    private DishStreamEventListener listener;

    @Test
    void publish_shouldDelegateChangeToStreamPublisher() {
        listener.publish(changeEvent());

        verify(publisher).publish(change());
    }
}
