package org.spring.divas.venue.feature.allergen;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.spring.divas.venue.common.BaseEntity;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Allergen extends BaseEntity {
    @NotBlank
    @Size(max=100)
    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;
}
