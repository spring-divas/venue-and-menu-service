package org.spring.divas.venue.feature.dish;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.spring.divas.venue.feature.category.Category;
import org.spring.divas.venue.feature.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class DishRatingUpdateTests {

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @AfterEach
    void tearDown() {
        dishRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void editDoesNotOverwriteRatingCommittedDuringIt() {
        Category category = categoryRepository.save(DishFixtures.category());
        Long dishId = dishRepository.save(Dish.builder()
                .venueId(1L)
                .category(category)
                .name("Soup")
                .price(BigDecimal.TEN)
                .build()).getId();

        TransactionTemplate editTransaction = new TransactionTemplate(transactionManager);
        TransactionTemplate ratingTransaction = new TransactionTemplate(transactionManager);
        ratingTransaction.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

        editTransaction.executeWithoutResult(edit -> {
            Dish edited = dishRepository.findById(dishId).orElseThrow();

            ratingTransaction.executeWithoutResult(rating -> {
                Dish rated = dishRepository.findById(dishId).orElseThrow();
                rated.setRating(4.5f);
                rated.setReviewCount(2);
            });

            edited.setName("Borscht");
        });

        Dish result = dishRepository.findById(dishId).orElseThrow();
        assertThat(result.getName()).isEqualTo("Borscht");
        assertThat(result.getRating()).isEqualTo(4.5f);
        assertThat(result.getReviewCount()).isEqualTo(2);
    }
}
