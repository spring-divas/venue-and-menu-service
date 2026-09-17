package org.spring.divas.venue.feature.venue;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max=150)
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @Size(max=255)
    @Column(name = "address", nullable = false)
    private String address;

    @ColumnDefault("0")
    @Column(name = "rating", nullable = false, precision = 10, scale = 2)
    private Float rating;

    @ColumnDefault("0")
    @Column(name = "review_count", nullable = false)
    private Integer reviewCount;
}

