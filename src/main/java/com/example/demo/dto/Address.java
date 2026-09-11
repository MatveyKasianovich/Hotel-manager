package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public class Address {

    @NotBlank(message = "houseNumber is required")
    private Long houseNumber;

    @NotBlank(message = "street is required")
    private String street;

    @NotBlank(message = "city is required")
    private String city;

    @NotBlank(message = "country is required")
    private String country;

    @NotBlank(message = "postCode is required")
    private String postCode;

    public Address(String postCode, String country, String city, String street, Long houseNumber) {
        this.postCode = postCode;
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
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

    public String getPostalCode() {
        return postCode;
    }

    public void setPostalCode(String postCode) {
        this.postCode = postCode;
    }
}
