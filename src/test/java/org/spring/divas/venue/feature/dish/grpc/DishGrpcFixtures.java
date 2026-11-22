package org.spring.divas.venue.feature.dish.grpc;

import java.util.List;
import lombok.experimental.UtilityClass;
import org.spring.divas.dish.v1.DishChange;
import org.spring.divas.dish.v1.SubscriptionRequest;
import org.spring.divas.venue.feature.MapperFixtures;
import org.spring.divas.venue.feature.dish.Dish;

@UtilityClass
public class DishGrpcFixtures {

    public static Dish dish() {
        return MapperFixtures.protoDish();
    }

    public static List<Dish> dishes() {
        return List.of(dish());
    }

    public static List<Dish> dishes(Dish dish) {
        return List.of(dish);
    }

    public static DishChange change() {
        return DishChange.newBuilder().setId(dish().getId()).setName(dish().getName()).build();
    }

    public static DishChangeEvent changeEvent() {
        return new DishChangeEvent(change());
    }

    public static SubscriptionRequest subscriptionRequest() {
        return SubscriptionRequest.getDefaultInstance();
    }

    public static RuntimeException closedStreamException() {
        return new RuntimeException("stream closed");
    }
}
