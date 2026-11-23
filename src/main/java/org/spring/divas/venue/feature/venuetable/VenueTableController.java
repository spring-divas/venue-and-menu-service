package org.spring.divas.venue.feature.venuetable;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/table")
public class VenueTableController {
    private final VenueTableService venueTableService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('ROLE_SENIOR ' + #p0.venueId)")
    @ResponseStatus(HttpStatus.CREATED)
    public VenueTableResponseDto create(@Valid @RequestBody VenueTableRequestDto dto) {
        return venueTableService.create(dto);
    }

    @GetMapping
    public List<VenueTableResponseDto> getAll() {
        return venueTableService.getAll();
    }

    @GetMapping("/{id}")
    public VenueTableResponseDto getById(@PathVariable Long id) {
        return venueTableService.getById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('ROLE_SENIOR ' + @venueTableServiceImpl.getById(#p0).venueId)")
    public void delete(@PathVariable Long id) {
        venueTableService.delete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') "
            + "or (hasAuthority('ROLE_SENIOR ' + @venueTableServiceImpl.getById(#p0).venueId) "
            + "and hasAuthority('ROLE_SENIOR ' + #p1.venueId))")
    public VenueTableResponseDto update(
            @PathVariable Long id,
            @Valid @RequestBody VenueTableRequestDto dto
    ) {
        return venueTableService.update(id, dto);
    }

}
