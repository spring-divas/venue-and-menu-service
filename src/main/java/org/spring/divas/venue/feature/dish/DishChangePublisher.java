package org.spring.divas.venue.feature.dish;

public interface DishChangePublisher {
    void publishCreated(Dish dish);
    void publishUpdated(Dish dish);
}
