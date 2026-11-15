package org.spring.divas.venue.feature.dish.grpc;

import lombok.RequiredArgsConstructor;
import org.spring.divas.venue.feature.dish.Dish;
import org.spring.divas.venue.feature.dish.DishChangePublisher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "dish.sync.transport", havingValue = "grpc")
@RequiredArgsConstructor
public class GrpcDishChangePublisher implements DishChangePublisher {
    private final ApplicationEventPublisher eventPublisher;
    private final DishProtoMapper dishProtoMapper;

    @Override
    public void publishCreated(Dish dish) {
        publish(dish);
    }

    @Override
    public void publishUpdated(Dish dish) {
        publish(dish);
    }

    private void publish(Dish dish) {
        eventPublisher.publishEvent(new DishChangeEvent(dishProtoMapper.toChange(dish)));
    }
}
