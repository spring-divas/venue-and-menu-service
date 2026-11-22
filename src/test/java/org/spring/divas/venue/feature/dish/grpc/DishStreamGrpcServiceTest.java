package org.spring.divas.venue.feature.dish.grpc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.spring.divas.venue.feature.dish.grpc.DishGrpcFixtures.change;
import static org.spring.divas.venue.feature.dish.grpc.DishGrpcFixtures.dish;
import static org.spring.divas.venue.feature.dish.grpc.DishGrpcFixtures.dishes;
import static org.spring.divas.venue.feature.dish.grpc.DishGrpcFixtures.subscriptionRequest;

import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.spring.divas.dish.v1.DishChange;
import org.spring.divas.venue.feature.dish.DishRepository;
import org.spring.divas.venue.feature.dish.Dish;

@ExtendWith(MockitoExtension.class)
class DishStreamGrpcServiceTest {

    @Mock
    private DishRepository dishRepository;

    @Mock
    private DishProtoMapper dishProtoMapper;

    @Mock
    private DishStreamPublisher publisher;

    @Mock
    private StreamObserver<DishChange> observer;

    @Mock
    private ServerCallStreamObserver<DishChange> serverObserver;

    @InjectMocks
    private DishStreamGrpcService service;

    @Test
    void subscribeDishes_shouldStreamExistingDishesAndSubscribeObserver() {
        Dish dish = dish();
        when(dishRepository.findAll()).thenReturn(dishes(dish));
        when(dishProtoMapper.toChange(dish)).thenReturn(change());

        service.subscribeDishes(subscriptionRequest(), observer);

        verify(observer).onNext(change());
        verify(publisher).subscribe(observer);
    }

    @Test
    void subscribeDishes_shouldUnsubscribeServerObserverOnCancel() {
        Dish dish = dish();
        when(dishRepository.findAll()).thenReturn(dishes(dish));
        when(dishProtoMapper.toChange(dish)).thenReturn(change());

        service.subscribeDishes(subscriptionRequest(), serverObserver);

        ArgumentCaptor<Runnable> cancelHandler = ArgumentCaptor.forClass(Runnable.class);
        verify(serverObserver).setOnCancelHandler(cancelHandler.capture());
        cancelHandler.getValue().run();
        verify(publisher).unsubscribe(serverObserver);
    }
}
