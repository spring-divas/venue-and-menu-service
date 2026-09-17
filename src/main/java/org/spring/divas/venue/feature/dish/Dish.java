package org.spring.divas.venue.feature.dish;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.spring.divas.venue.feature.category.Category;
import org.spring.divas.venue.feature.ingredient.Ingredient;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "venue_id", nullable = false)
    private Long venueId;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @NotBlank
    @Size(max = 150)
    @Column(name = "name", length = 150, nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "price", nullable = false , precision = 10, scale = 2)
    private BigDecimal price;

    @PositiveOrZero
    @Column(name = "rating", nullable = false, precision = 10, scale = 2)
    private Float rating;

    @PositiveOrZero
    @ColumnDefault("0")
    @Column(name = "review_count", nullable = false)
    private Integer reviewCount;

    @ManyToMany
    @JoinTable(name = "dish_ingredient")
    private Set<Ingredient> ingredients = new HashSet<>();
}
