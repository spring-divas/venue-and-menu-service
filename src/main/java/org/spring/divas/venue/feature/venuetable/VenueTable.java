package org.spring.divas.venue.feature.venuetable;

import jakarta.persistence.*;
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
public class VenueTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "venue_id", nullable = false)
    private Long venueId;

    @ColumnDefault("true")
    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable;
}

