package com.example.demo;

import com.example.demo.controller.NameCount;
import com.example.demo.repository.AmenityEntity;
import com.example.demo.repository.AmenityRepository;
import com.example.demo.repository.HotelEntity;
import com.example.demo.repository.HotelRepository;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;


import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class HotelRepositoryIT {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private AmenityRepository amenityRepository;

    private HotelEntity berlinHotel;
    private HotelEntity parisHotel;

    @BeforeEach
    void setUp() {
        AmenityEntity wifi = amenityRepository.save(new AmenityEntity("wifi"));
        AmenityEntity pool = amenityRepository.save(new AmenityEntity("pool"));

        berlinHotel = new HotelEntity(
                null, "Grand Plaza", "City center hotel", "Hilton",
                1L, "Unter den Linden", "Berlin", "Germany", "10115",
                "+49301234567", "info@grandplaza.com",
                LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2)
        );
        berlinHotel.addAmenity(wifi);

        parisHotel = new HotelEntity(
                null, "Eiffel Suites", "Near the tower", "Marriott",
                5L, "Avenue Anatole France", "Paris", "France", "75007",
                "+33123456789", "contact@eiffelsuites.com",
                LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(4)
        );
        parisHotel.addAmenity(pool);

        hotelRepository.save(berlinHotel);
        hotelRepository.save(parisHotel);
    }

    @Test
    void searchByFilter_withNameSubstring_isCaseInsensitiveAndMatchesPartial() {
        List<HotelEntity> result = hotelRepository.searchByFilter(
                "plaza", null, null, null, null);

        assertThat(result)
                .extracting(HotelEntity::getName)
                .containsExactly("Grand Plaza");
    }

    @Test
    void searchByFilter_withCityAndCountry_returnsOnlyMatchingHotel() {
        List<HotelEntity> result = hotelRepository.searchByFilter(
                null, null, "paris", "france", null);

        assertThat(result)
                .extracting(HotelEntity::getName)
                .containsExactly("Eiffel Suites");
    }

    @Test
    void searchByFilter_withAmenity_returnsHotelsHavingThatAmenity() {
        List<HotelEntity> result = hotelRepository.searchByFilter(
                null, null, null, null, List.of("wifi"));

        assertThat(result)
                .extracting(HotelEntity::getName)
                .containsExactly("Grand Plaza");
    }

    @Test
    void searchByFilter_withNoMatches_returnsEmptyList() {
        List<HotelEntity> result = hotelRepository.searchByFilter(
                "Nonexistent", null, null, null, null);

        assertThat(result).isEmpty();
    }

    @Test
    void searchByFilter_withAllFiltersNull_returnsAllHotels() {
        List<HotelEntity> result = hotelRepository.searchByFilter(
                null, null, null, null, null);

        assertThat(result).hasSize(2);
    }

    @Test
    void countGroupedByBrand_returnsOneCountPerDistinctBrand() {
        List<NameCount> result = hotelRepository.countGroupedByBrand();

        assertThat(result)
                .extracting(NameCount::getName, NameCount::getCount)
                .containsExactlyInAnyOrder(
                        Tuple.tuple("Hilton", 1L),
                        Tuple.tuple("Marriott", 1L)
                );
    }
}