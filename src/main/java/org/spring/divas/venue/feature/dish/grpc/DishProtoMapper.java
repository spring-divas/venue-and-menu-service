package org.spring.divas.venue.feature.dish.grpc;

import com.google.protobuf.ByteString;
import org.spring.divas.dish.v1.DecimalValue;
import org.spring.divas.dish.v1.DishChange;
import org.spring.divas.venue.feature.dish.Dish;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DishProtoMapper {
    public DishChange toChange(Dish dish) {
        BigDecimal price = dish.getPrice();
        return DishChange.newBuilder()
                .setId(dish.getId())
                .setName(dish.getName())
                .setPrice(DecimalValue.newBuilder()
                        .setScale(price.scale())
                        .setPrecision(price.precision())
                        .setValue(ByteString.copyFrom(price.unscaledValue().toByteArray()))
                        .build())
                .build();
    }
}
