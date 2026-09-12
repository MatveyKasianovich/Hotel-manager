package com.example.demo.repository;

import com.example.demo.controller.NameCount;
import com.example.demo.service.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<HotelEntity, Long> {

    @Query("""
        SELECT DISTINCT h FROM HotelEntity h
        LEFT JOIN h.amenities a
        WHERE (:name IS NULL OR LOWER(h.name) LIKE LOWER(CONCAT('%', :name, '%')))
          AND (:brand IS NULL OR LOWER(h.brand) LIKE LOWER(CONCAT('%', :brand, '%')))
          AND (:city IS NULL OR LOWER(h.city) LIKE LOWER(CONCAT('%', :city, '%')))
          AND (:country IS NULL OR LOWER(h.country) LIKE LOWER(CONCAT('%', :country, '%')))
          AND (:amenities IS NULL OR LOWER(a.name) IN :amenities)
        """)
    List<HotelEntity> searchByFilter(@Param("name") String name,
                             @Param("brand") String brand,
                             @Param("city") String city,
                             @Param("country") String country,
                             @Param("amenities") List<String> amenities);

    @Query("SELECT h.brand AS name, COUNT(h) AS count FROM HotelEntity h WHERE h.brand IS NOT NULL GROUP BY h.brand")
    List<NameCount> countGroupedByBrand();

    @Query("SELECT h.city AS name, COUNT(h) AS count FROM HotelEntity h WHERE h.city IS NOT NULL GROUP BY h.city")
    List<NameCount> countGroupedByCity();

    @Query("SELECT h.country AS name, COUNT(h) AS count FROM HotelEntity h WHERE h.country IS NOT NULL GROUP BY h.country")
    List<NameCount> countGroupedByCountry();

    @Query("SELECT a.name AS name, COUNT(h) AS count FROM HotelEntity h JOIN h.amenities a GROUP BY a.name")
    List<NameCount> countGroupedByAmenity();
}
