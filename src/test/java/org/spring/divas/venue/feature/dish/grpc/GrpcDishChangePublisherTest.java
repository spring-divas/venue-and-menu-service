package org.spring.divas.venue.feature.dish.grpc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.spring.divas.venue.feature.dish.grpc.DishGrpcAssertions.assertChangeEvent;
import static org.spring.divas.venue.feature.dish.grpc.DishGrpcFixtures.change;
import static org.spring.divas.venue.feature.dish.grpc.DishGrpcFixtures.dish;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.spring.divas.venue.feature.dish.Dish;
import org.springframework.context.ApplicationEventPublisher;

@ExtendWith(MockitoExtension.class)
class GrpcDishChangePublisherTest {

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private DishProtoMapper dishProtoMapper;

    @InjectMocks
    private GrpcDishChangePublisher publisher;

    @Test
    void publishCreated_shouldPublishDishChangeEvent() {
        Dish dish = dish();
        when(dishProtoMapper.toChange(dish)).thenReturn(change());

        publisher.publishCreated(dish);

        assertPublishedChangeEvent(dish);
    }

    @Test
    void publishUpdated_shouldPublishDishChangeEvent() {
        Dish dish = dish();
        when(dishProtoMapper.toChange(dish)).thenReturn(change());

        publisher.publishUpdated(dish);

        assertPublishedChangeEvent(dish);
    }

    private void assertPublishedChangeEvent(Dish dish) {
        ArgumentCaptor<DishChangeEvent> eventCaptor = ArgumentCaptor.forClass(DishChangeEvent.class);
        verify(eventPublisher).publishEvent(eventCaptor.capture());
        verify(dishProtoMapper).toChange(dish);
        assertChangeEvent(eventCaptor.getValue());
    }
}
