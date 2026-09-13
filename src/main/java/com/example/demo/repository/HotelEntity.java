package com.example.demo.repository;


import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "hotel")
public class HotelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name="description")
    private String description;

    private String brand;


    @Column(name = "house_number")
    private Long houseNumber;

    @Column(name = "street")
    private String street;

    private String city;
    private String country;

    @Column(name = "post_code")
    private String postCode;

    @Column(name = "phone")
    private String phone;

    private String email;

    @Column(name = "check_in")
    private LocalTime checkIn;

    @Column(name = "check_out")
    private LocalTime checkOut;

    @ManyToMany(cascade = { CascadeType.PERSIST})
    @JoinTable(
            name = "hotel_amenity",
            joinColumns = @JoinColumn(name = "hotel_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private Set<AmenityEntity> amenities = new LinkedHashSet<>();

    public HotelEntity() {
    }

    public HotelEntity(Long id, String name, String description, String brand, Long houseNumber, String street, String city, String country, String postCode, String phone, String email, LocalTime checkIn, LocalTime checkOut, Set<AmenityEntity> amenities) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.houseNumber = houseNumber;
        this.street = street;
        this.city = city;
        this.country = country;
        this.postCode = postCode;
        this.phone = phone;
        this.email = email;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.amenities = amenities;
    }

    public HotelEntity(Long id, String name, String description, String brand, Long houseNumber, String street, String city, String country, String postCode, String phone, String email, LocalTime checkIn, LocalTime checkOut) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.houseNumber = houseNumber;
        this.street = street;
        this.city = city;
        this.country = country;
        this.postCode = postCode;
        this.phone = phone;
        this.email = email;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public void addAmenity(AmenityEntity amenity) {
        this.amenities.add(amenity);
        amenity.getHotels().add(this);
    }

    public void removeAmenity(AmenityEntity amenity) {
        this.amenities.remove(amenity);
        amenity.getHotels().remove(this);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Long getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Long houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalTime checkOut) {
        this.checkOut = checkOut;
    }

    public Set<AmenityEntity> getAmenities() {
        return amenities;
    }

    public void setAmenities(Set<AmenityEntity> amenities) {
        this.amenities = amenities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HotelEntity other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
