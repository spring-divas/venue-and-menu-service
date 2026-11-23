package org.spring.divas.venue;

import au.com.dius.pact.provider.MessageAndMetadata;
import au.com.dius.pact.provider.PactVerifyProvider;
import au.com.dius.pact.provider.junit5.MessageTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Consumer;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.spring.divas.venue.feature.dish.Dish;
import org.spring.divas.venue.feature.dish.DishEventMapper;
import org.spring.divas.venue.feature.dish.DishEventMapperImpl;
import org.spring.divas.venue.feature.outbox.OutboxEvent;
import tools.jackson.databind.json.JsonMapper;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Provider("venue-and-menu-service")
@Consumer("order-service")
@PactFolder("${pactfolder.path}")
class DishEventsPactVerificationTest {

    private final DishEventMapper dishEventMapper = createDishEventMapper();

    private static DishEventMapper createDishEventMapper() {
        DishEventMapperImpl mapper = new DishEventMapperImpl();
        mapper.setObjectMapper(new JsonMapper());
        return mapper;
    }

    @BeforeEach
    @SuppressWarnings("JUnitMalformedDeclaration")
    void configureTarget(PactVerificationContext context) {
        context.setTarget(new MessageTestTarget(List.of(getClass().getPackageName())));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void verifiesPact(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @PactVerifyProvider("a DishCreated event")
    MessageAndMetadata dishCreatedEvent() {
        return message(dishEventMapper.toOutboxEvent(dishEventMapper.toCreatedEvent(dish())));
    }

    @PactVerifyProvider("a DishUpdated event")
    MessageAndMetadata dishUpdatedEvent() {
        return message(dishEventMapper.toOutboxEvent(dishEventMapper.toUpdatedEvent(dish())));
    }

    private Dish dish() {
        return Dish.builder()
                .id(123L)
                .venueId(456L)
                .name("Borscht")
                .price(new BigDecimal("120.50"))
                .build();
    }

    private MessageAndMetadata message(OutboxEvent event) {
        return new MessageAndMetadata(
                event.getPayload().getBytes(StandardCharsets.UTF_8),
                Map.of("eventType", event.getEventType())
        );
    }
}
