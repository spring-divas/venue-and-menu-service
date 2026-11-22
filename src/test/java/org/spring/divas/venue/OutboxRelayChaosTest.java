package org.spring.divas.venue;

import de.codecentric.spring.boot.chaos.monkey.configuration.AssaultException;
import de.codecentric.spring.boot.chaos.monkey.configuration.AssaultProperties;
import de.codecentric.spring.boot.chaos.monkey.configuration.ChaosMonkeySettings;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.spring.divas.venue.feature.category.Category;
import org.spring.divas.venue.feature.category.CategoryRepository;
import org.spring.divas.venue.feature.dish.DishRepository;
import org.spring.divas.venue.feature.dish.DishService;
import org.spring.divas.venue.feature.outbox.OutboxEvent;
import org.spring.divas.venue.feature.outbox.OutboxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@ActiveProfiles("chaos-monkey")
@TestPropertySource(properties = {
        "chaos.monkey.watcher.service=false",
        "chaos.monkey.watcher.rest-controller=false",
        "chaos.monkey.watcher.beans=pollingOutboxScheduler",
        "spring.grpc.server.port=0"
})
class OutboxRelayChaosTest extends KafkaIntegrationTest {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OutboxRepository outboxRepository;

    @Autowired
    private ChaosMonkeySettings chaosMonkey;

    private Category category;

    @BeforeEach
    void setUp() {
        category = categoryRepository.save(DishFixtures.category());

        AssaultProperties assaults = chaosMonkey.getAssaultProperties();
        assaults.setLevel(1);
        assaults.setDeterministic(true);
        assaults.setLatencyActive(false);
        assaults.setExceptionsActive(true);
        assaults.setException(new AssaultException());
    }

    @AfterEach
    void tearDown() {
        chaosMonkey.getChaosMonkeyProperties().setEnabled(false);
        outboxRepository.deleteAll();
        dishRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void dishChangesAreKeptWhileRelayIsDownAndPublishedAfterRecovery() {
        chaosMonkey.getChaosMonkeyProperties().setEnabled(true);

        dishService.create(DishFixtures.request(category.getId()));

        assertThat(dishRepository.findAll()).hasSize(1);
        OutboxEvent outbox = outboxRepository.findAll().getFirst();

        await().during(Duration.ofMillis(1500))
                .atMost(Duration.ofSeconds(3))
                .until(() -> !outboxRepository.findById(outbox.getId()).orElseThrow().isProcessed());

        try (var consumer = consumer("dish-events")) {
            chaosMonkey.getChaosMonkeyProperties().setEnabled(false);

            var dishEvent = awaitRecord(consumer, outbox.getPayload());
            assertThat(dishEvent.key()).isEqualTo(outbox.getAggregateId());
        }
        await().atMost(Duration.ofSeconds(5))
                .until(() -> outboxRepository.findById(outbox.getId()).orElseThrow().isProcessed());
    }
}
