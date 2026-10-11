package org.spring.divas.venue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.spring.divas.venue.feature.category.Category;
import org.spring.divas.venue.feature.category.CategoryRepository;
import org.spring.divas.venue.feature.dish.DishEventMapper;
import org.spring.divas.venue.feature.dish.DishRepository;
import org.spring.divas.venue.feature.dish.DishResponseMapper;
import org.spring.divas.venue.feature.dish.DishService;
import org.spring.divas.venue.feature.dish.DishUpdatedEvent;
import org.spring.divas.venue.feature.outbox.OutboxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

class DishOutboxTests extends KafkaIntegrationTest {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OutboxRepository outboxRepository;

    @Autowired
    private DishEventMapper dishEventMapper;

    @MockitoSpyBean
    private DishResponseMapper dishResponseMapper;

    private Category category;

    @BeforeEach
    void setUp() {
        category = categoryRepository.save(DishFixtures.category());
    }

    @AfterEach
    void tearDown() {
        outboxRepository.deleteAll();
        dishRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void createSavesDishAndOutbox() {
        dishService.create(DishFixtures.request(category.getId()));
        assertThat(dishRepository.findAll()).hasSize(1);
        assertThat(outboxRepository.findAll()).hasSize(1);
    }

    @Test
    void failureRollsBackBoth() {
        doThrow(new RuntimeException()).when(dishResponseMapper).toDto(any());
        assertThatThrownBy(() -> dishService.create(DishFixtures.request(category.getId())))
                .isInstanceOf(RuntimeException.class);
        assertThat(dishRepository.findAll()).isEmpty();
        assertThat(outboxRepository.findAll()).isEmpty();
    }

    @Test
    void relaySendsEventAndMarksProcessed() {
        var event = dishEventMapper.toUpdatedEvent(DishFixtures.dish());
        var outbox = outboxRepository.save(dishEventMapper.toOutboxEvent(event));
        try (var consumer = consumer("dish-events")) {
            var record = awaitRecord(consumer, event.eventId().toString());
            assertThat(record.key()).isEqualTo(outbox.getAggregateId());
            assertThat(read(record, DishUpdatedEvent.class)).isEqualTo(event);
        }
        await().atMost(Duration.ofSeconds(5))
                .until(() -> outboxRepository.findById(outbox.getId()).orElseThrow().isProcessed());
    }
}