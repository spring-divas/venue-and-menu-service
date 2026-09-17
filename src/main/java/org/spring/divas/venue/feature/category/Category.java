package org.spring.divas.venue.feature.category;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "venueId", nullable = false)
    private Integer venueId;

    @NotBlank
    @Size(max = 150)
    @Column(name = "name", nullable = false)
    private String name;
}
