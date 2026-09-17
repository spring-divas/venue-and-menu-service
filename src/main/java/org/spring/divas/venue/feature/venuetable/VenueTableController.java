package org.spring.divas.venue.feature.venuetable;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/table")
public class VenueTableController {

    @PostMapping
    public VenueTableResponseDto create(@RequestBody VenueTableRequestDto dto) {
        return null;
    }

    @GetMapping
    public List<VenueTableResponseDto> getAll() {
        return null;
    }

    @GetMapping("/{id}")
    public VenueTableResponseDto getById(@PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public VenueTableResponseDto delete(@PathVariable Long id) {
        return null;
    }

    @PostMapping("/{id}")
    public VenueTableResponseDto update(
            @PathVariable Long id,
            @RequestBody VenueTableRequestDto dto
    ) {
        return null;
    }

}
