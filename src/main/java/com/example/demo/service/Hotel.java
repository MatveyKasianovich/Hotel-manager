package com.example.demo.service;

import com.example.demo.dto.Address;
import com.example.demo.dto.ArrivalTime;
import com.example.demo.dto.Contacts;

public class Hotel {
    private Long id;
    private String name;
    private String description;
    public String hotelBrand;
    private Address address;
    private Contacts contacts;
    private ArrivalTime arrivalTime;

    public Hotel(String name, String description, String hotelBrand, Address address, Contacts contacts, ArrivalTime arrivalTime) {
        this.name = name;
        this.description = description;
        this.hotelBrand = hotelBrand;
        this.address = address;
        this.contacts = contacts;
        this.arrivalTime = arrivalTime;
    }

    public Hotel(Long id, String name, String description, String hotelBrand, Address address, Contacts contacts, ArrivalTime arrivalTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.hotelBrand = hotelBrand;
        this.address = address;
        this.contacts = contacts;
        this.arrivalTime = arrivalTime;
    }

    public String addressToString() {

        StringBuilder sb = new StringBuilder();

        sb.append(this.getAddress().getHouseNumber()).append(" ");
        sb.append(this.getAddress().getStreet());
        sb.append(", ").append(this.getAddress().getCity());
        sb.append(", ").append(this.getAddress().getPostalCode());
        sb.append(", ").append(this.getAddress().getCountry());

        return sb.toString();
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

    public String getHotelBrand() {
        return hotelBrand;
    }

    public void setHotelBrand(String hotelBrand) {
        this.hotelBrand = hotelBrand;
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
