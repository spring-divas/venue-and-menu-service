package org.spring.divas.venue.feature.dish;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Long> {
    @EntityGraph(attributePaths = {"ingredients", "category"})
    List<Dish> findByVenueId(Long venueId);
}
