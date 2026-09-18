package org.spring.divas.venue.feature.venuetable;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/table")
public class VenueTableController {
    private final VenueTableService venueTableService;

    @PostMapping
    public VenueTableResponseDto create(@RequestBody VenueTableRequestDto dto) {
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
    public void delete(@PathVariable Long id) {
        venueTableService.delete(id);
    }

    @PutMapping("/{id}")
    public VenueTableResponseDto update(
            @PathVariable Long id,
            @RequestBody VenueTableRequestDto dto
    ) {
        return venueTableService.update(id, dto);
    }

}
