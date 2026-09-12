package com.example.demo.service;

import com.example.demo.controller.NameCount;
import com.example.demo.mapper.Mapper;
import com.example.demo.repository.AmenityEntity;
import com.example.demo.repository.AmenityRepository;
import com.example.demo.repository.HotelEntity;
import com.example.demo.repository.HotelRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;
    private final Mapper mapper;
    private final AmenityRepository amenityRepository;

    public HotelService(HotelRepository hotelRepository, Mapper mapper, AmenityRepository amenityRepository) {
        this.hotelRepository = hotelRepository;
        this.mapper = mapper;
        this.amenityRepository = amenityRepository;
    }

    @Transactional
    public Hotel createHotel(Hotel hotelToCreate) {
        HotelEntity hotelEntity=hotelRepository.save(mapper.businessToEntity(hotelToCreate));
        return mapper.entityToBusiness(hotelEntity);
    }

    public List<Hotel> findAll() {
        List<HotelEntity> hotelEntities = hotelRepository.findAll();
        return hotelEntities.stream()
                .map(mapper::entityToBusiness)
                .collect(Collectors.toUnmodifiableList());
    }

    public HotelEntity findHotelById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Not found hotel with id: %s".formatted(id)));
    }

    public List<Hotel> findHotelsByFilter(String name, String brand, String city, String country, List<String> amenities) {
        List<String> normalizedAmenities = (amenities == null)
                ? null
                : amenities.stream().map(String::toLowerCase).toList();

        List<HotelEntity> entities = hotelRepository.searchByFilter(name, brand, city, country, normalizedAmenities);
        return  entities.stream()
                .map(mapper::entityToBusiness)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public HotelEntity addAmenityToHotel(Long id, List<String> amenities) {
        HotelEntity entity = hotelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Not found hotel with id: %s".formatted(id)));

        amenities.stream()
                .map(this::findOrCreateAmenity)
                .forEach(entity::addAmenity);

        return hotelRepository.save(entity);
    }

    private AmenityEntity findOrCreateAmenity(String name) {
        return amenityRepository.findByNameIgnoreCase(name)
                .orElseGet(() -> amenityRepository.save(new AmenityEntity(name)));
    }

    public Map<String, Long> histogram(String param) {
        List<NameCount> results = switch (param.toLowerCase()) {
            case "brand" -> hotelRepository.countGroupedByBrand();
            case "city" -> hotelRepository.countGroupedByCity();
            case "country" -> hotelRepository.countGroupedByCountry();
            case "amenities" -> hotelRepository.countGroupedByAmenity();
            default -> throw new IllegalArgumentException(
                    "Unsupported histogram parameter"
            );
        };

        return results.stream()
                .collect(Collectors.toMap(NameCount::getName, NameCount::getCount, (a, b) -> a, LinkedHashMap::new));
    }
}
