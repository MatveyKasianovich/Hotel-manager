package com.example.demo.repository;


import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "amenity")
public class AmenityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String name;

    @ManyToMany(mappedBy = "amenities")
    private Set<HotelEntity> hotels = new LinkedHashSet<>();

    public AmenityEntity() {
    }

    public AmenityEntity(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<HotelEntity> getHotels() {
        return hotels;
    }

    public void setHotels(Set<HotelEntity> hotels) {
        this.hotels = hotels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AmenityEntity other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
