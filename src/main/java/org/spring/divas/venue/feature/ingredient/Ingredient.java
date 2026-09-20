package org.spring.divas.venue.feature.ingredient;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.spring.divas.venue.common.persistance.BaseEntity;
import org.spring.divas.venue.feature.allergen.Allergen;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient extends BaseEntity {
    @NotBlank
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @ColumnDefault("0")
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToMany
    @JoinTable(name = "ingredient_allergen")
    @Builder.Default
    Set<Allergen> allergens = new HashSet<>();
}
