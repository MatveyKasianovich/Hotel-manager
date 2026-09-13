package com.example.demo.controller;

import com.example.demo.dto.Address;
import com.example.demo.dto.ArrivalTime;
import com.example.demo.dto.Contacts;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public class HotelRequestDto {

    @NotBlank(message = "name is required")
    private String name;

    private String description;

    @NotBlank(message = "brand is required")
    public String brand;

    @Valid
    private Address address;

    @Valid
    private Contacts contacts;

    @Valid
    private ArrivalTime arrivalTime;

    public HotelRequestDto(String name, String description, String brand, Address address, Contacts contacts, ArrivalTime arrivalTime) {
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.address = address;
        this.contacts = contacts;
        this.arrivalTime = arrivalTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHotelName() {
        return name;
    }

    public void setHotelName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHotelBrand() {
        return brand;
    }

    public void setHotelBrand(String brand) {
        this.brand = brand;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    public ArrivalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(ArrivalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
