package org.spring.divas.venue.feature.venue;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venue")
public class VenueController {

    @PostMapping
    public VenueResponseDto create(@RequestBody VenueRequestDto dto) {
        return null;
    }

    @GetMapping
    public List<VenueResponseDto> getAll() {
        return null;
    }

    @GetMapping("/{id}")
    public VenueResponseDto getById(@PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public VenueResponseDto delete(@PathVariable Long id) {
        return null;
    }

    @PostMapping("/{id}")
    public VenueResponseDto update(
            @PathVariable Long id,
            @RequestBody VenueRequestDto dto
    ) {
        return null;
    }

}
