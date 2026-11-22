package org.spring.divas.venue.feature.dish.grpc;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DishGrpcAssertions {

    public static void assertChangeEvent(DishChangeEvent actual) {
        assertThat(actual.change()).isEqualTo(DishGrpcFixtures.change());
    }
}
