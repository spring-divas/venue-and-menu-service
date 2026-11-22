package org.spring.divas.venue.feature.venue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.spring.divas.venue.common.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VenueServiceImplTest {

    @Mock
    private VenueRepository venueRepository;

    @Mock
    private VenueResponseMapper venueResponseMapper;

    @Mock
    private VenueRequestMapper venueRequestMapper;

    private VenueServiceImpl venueService;

    @BeforeEach
    void setUp() {
        venueService = new VenueServiceImpl(venueRepository, venueResponseMapper, venueRequestMapper);
    }

    @Test
    void create_shouldReturnSavedDto() {
        VenueRequestDto request = new VenueRequestDto("Central Cafe", "Kyiv, Khreshchatyk 1");
        Venue entity = Venue.builder().id(1L).name("Central Cafe").address("Kyiv, Khreshchatyk 1").build();
        VenueResponseDto expectedResponse = new VenueResponseDto(1L, "Central Cafe", "Kyiv, Khreshchatyk 1", 5.0f, 10);

        when(venueRequestMapper.toEntity(request)).thenReturn(entity);
        when(venueRepository.save(entity)).thenReturn(entity);
        when(venueResponseMapper.toDto(entity)).thenReturn(expectedResponse);

        VenueResponseDto actual = venueService.create(request);

        assertThat(actual).isEqualTo(expectedResponse);
        verify(venueRepository).save(entity);
    }

    @Test
    void getAll_shouldReturnListOfVenues() {
        Venue venue = Venue.builder().id(1L).name("Cafe").build();
        VenueResponseDto dto = new VenueResponseDto(1L, "Cafe", "Addr", 4.0f, 2);

        when(venueRepository.findAll()).thenReturn(List.of(venue));
        when(venueResponseMapper.toDto(venue)).thenReturn(dto);

        List<VenueResponseDto> result = venueService.getAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(dto);
    }

    @Test
    void getById_shouldReturnVenueWhenFound() {
        Venue venue = Venue.builder().id(1L).name("Cafe").build();
        VenueResponseDto dto = new VenueResponseDto(1L, "Cafe", "Addr", 4.0f, 2);

        when(venueRepository.findById(1L)).thenReturn(Optional.of(venue));
        when(venueResponseMapper.toDto(venue)).thenReturn(dto);

        VenueResponseDto result = venueService.getById(1L);

        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getById_shouldThrowWhenNotFound() {
        when(venueRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> venueService.getById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Venue not found.");
    }

    @Test
    void delete_shouldCallRepository() {
        venueService.delete(1L);

        verify(venueRepository).deleteById(1L);
    }

    @Test
    void update_shouldUpdateAndReturnDto() {
        Venue venue = Venue.builder().id(1L).name("Old Name").address("Old Address").build();
        VenueRequestDto updateDto = new VenueRequestDto("New Name", "New Address");
        VenueResponseDto expectedResponse = new VenueResponseDto(1L, "New Name", "New Address", 4.0f, 2);

        when(venueRepository.findById(1L)).thenReturn(Optional.of(venue));
        when(venueRepository.save(venue)).thenReturn(venue);
        when(venueResponseMapper.toDto(venue)).thenReturn(expectedResponse);

        VenueResponseDto result = venueService.update(1L, updateDto);

        assertThat(result).isEqualTo(expectedResponse);
        assertThat(venue.getName()).isEqualTo("New Name");
        assertThat(venue.getAddress()).isEqualTo("New Address");
    }

    @Test
    void update_shouldThrowWhenNotFound() {
        VenueRequestDto updateDto = new VenueRequestDto("New Name", "New Address");
        when(venueRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> venueService.update(99L, updateDto))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void updateRating_shouldReturnTrueWhenVenueExists() {
        Venue venue = Venue.builder().id(1L).rating(3.0f).reviewCount(1).build();
        when(venueRepository.findById(1L)).thenReturn(Optional.of(venue));

        boolean updated = venueService.updateRating(1L, 4.5f, 2);

        assertThat(updated).isTrue();
        assertThat(venue.getRating()).isEqualTo(4.5f);
        assertThat(venue.getReviewCount()).isEqualTo(2);
    }

    @Test
    void updateRating_shouldReturnFalseWhenVenueNotFound() {
        when(venueRepository.findById(99L)).thenReturn(Optional.empty());

        boolean updated = venueService.updateRating(99L, 4.5f, 2);

        assertThat(updated).isFalse();
    }
}
