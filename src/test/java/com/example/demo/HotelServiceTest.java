package com.example.demo;


import com.example.demo.controller.NameCount;
import com.example.demo.dto.Address;
import com.example.demo.dto.ArrivalTime;
import com.example.demo.dto.Contacts;
import com.example.demo.mapper.Mapper;
import com.example.demo.repository.AmenityEntity;
import com.example.demo.repository.AmenityRepository;
import com.example.demo.repository.HotelEntity;
import com.example.demo.repository.HotelRepository;
import com.example.demo.service.Hotel;
import com.example.demo.service.HotelService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private Mapper mapper;

    @Mock
    private AmenityRepository amenityRepository;

    @InjectMocks
    private HotelService hotelService;

    private Hotel businessHotel;
    private HotelEntity entity;

    @BeforeEach
    void setUp() {
        businessHotel = new Hotel(
                "Grand Plaza",
                "A hotel in the city center",
                "Hilton",
                new Address("10115", "Germany", "Berlin", "Unter den Linden", 1L),
                new Contacts("+49301234567", "info@grandplaza.com"),
                new ArrivalTime(LocalTime.of(14, 0), LocalTime.of(12, 0))
        );

        entity = new HotelEntity(
                1L, "Grand Plaza", "A hotel in the city center", "Hilton",
                1L, "Unter den Linden", "Berlin", "Germany", "10115",
                "+49301234567", "info@grandplaza.com",
                LocalTime.of(14, 0), LocalTime.of(12, 0)
        );
    }

    @Test
    void createHotel_savesEntityAndReturnsMappedBusinessObject() {
        when(mapper.businessToEntity(businessHotel)).thenReturn(entity);
        when(hotelRepository.save(entity)).thenReturn(entity);
        when(mapper.entityToBusiness(entity)).thenReturn(businessHotel);

        Hotel result = hotelService.createHotel(businessHotel);

        assertThat(result).isEqualTo(businessHotel);
        verify(hotelRepository, times(1)).save(entity);
    }

    @Test
    void findHotelById_whenNotFound_throwsEntityNotFoundException() {
        when(hotelRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> hotelService.findHotelById(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");

        verify(hotelRepository, times(1)).findById(99L);
    }

    @Test
    void addAmenityToHotel_whenHotelNotFound_throwsException() {
        when(hotelRepository.findById(5L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> hotelService.addAmenityToHotel(5L, List.of("wifi")))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("5");

        verify(amenityRepository, never()).save(any());
        verify(hotelRepository, never()).save(any());
    }

    @Test
    void addAmenityToHotel_whenAmenityExists_reusesIt() {
        AmenityEntity existing = new AmenityEntity("wifi");
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(amenityRepository.findByNameIgnoreCase("wifi")).thenReturn(Optional.of(existing));
        when(hotelRepository.save(entity)).thenReturn(entity);

        HotelEntity result = hotelService.addAmenityToHotel(1L, List.of("wifi"));

        assertThat(result.getAmenities()).contains(existing);
        verify(amenityRepository, never()).save(any());
    }

    @Test
    void histogram_withUnsupportedParam_throwsIllegalArgumentException() {
        assertThatThrownBy(() -> hotelService.histogram("unknown"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unsupported histogram parameter");
    }

    @Test
    void histogram_withBrand_returnsMapOfBrandCounts() {
        NameCount hilton = nameCount("Hilton", 3L);
        NameCount marriott = nameCount("Marriott", 2L);
        when(hotelRepository.countGroupedByBrand()).thenReturn(List.of(hilton, marriott));

        Map<String, Long> result = hotelService.histogram("brand");

        assertThat(result)
                .containsEntry("Hilton", 3L)
                .containsEntry("Marriott", 2L);
    }

    private NameCount nameCount(String name, Long count) {
        return new NameCount() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public Long getCount() {
                return count;
            }
        };
    }
}