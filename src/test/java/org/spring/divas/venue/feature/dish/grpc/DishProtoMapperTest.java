package org.spring.divas.venue.feature.dish.grpc;

import static org.spring.divas.venue.feature.MapperAssertions.assertDishChange;
import static org.spring.divas.venue.feature.MapperFixtures.protoDish;

import org.junit.jupiter.api.Test;
import org.spring.divas.dish.v1.DishChange;
import org.spring.divas.venue.feature.dish.Dish;

class DishProtoMapperTest {

    @Test
    void toChange_shouldMapDishAndDecimalPrice() {
        Dish dish = protoDish();
        DishChange change = new DishProtoMapper().toChange(dish);
        assertDishChange(change);
    }
}
