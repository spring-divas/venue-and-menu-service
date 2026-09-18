package org.spring.divas.venue.feature.venue;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/venue")
public class VenueController {
    private final VenueService venueService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VenueResponseDto create(@RequestBody VenueRequestDto dto) {
        return venueService.create(dto);
    }

    @GetMapping
    public List<VenueResponseDto> getAll() {
        return venueService.getAll();
    }

    @GetMapping("/{id}")
    public VenueResponseDto getById(@PathVariable Long id) {
        return venueService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        venueService.delete(id);
    }

    @PutMapping("/{id}")
    public VenueResponseDto update(
            @PathVariable Long id,
            @RequestBody VenueRequestDto dto
    ) {
        return venueService.update(id, dto);
    }

}
