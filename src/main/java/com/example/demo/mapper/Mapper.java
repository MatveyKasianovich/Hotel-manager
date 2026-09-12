package com.example.demo.mapper;

import com.example.demo.controller.ExtendHotelResponseDto;
import com.example.demo.controller.HotelRequestDto;
import com.example.demo.controller.HotelResponseDto;
import com.example.demo.dto.Address;
import com.example.demo.dto.ArrivalTime;
import com.example.demo.dto.Contacts;
import com.example.demo.repository.HotelEntity;
import com.example.demo.service.Hotel;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public final Hotel requestDtoToBusiness(HotelRequestDto hotelRequestDto) {
        return new Hotel(
                hotelRequestDto.getName(),
                hotelRequestDto.getDescription(),
                hotelRequestDto.getHotelBrand(),
                hotelRequestDto.getAddress(),
                hotelRequestDto.getContacts(),
                hotelRequestDto.getArrivalTime()
        );
    }

    public final HotelResponseDto businessToResponseDto(Hotel hotel) {
        return new HotelResponseDto(
                hotel.getId(),
                hotel.getName(),
                hotel.getDescription(),
                hotel.addressToString(),
                hotel.getContacts().getPhone()
        );
    }

    public final HotelEntity businessToEntity(Hotel hotel) {
        return new HotelEntity(
            hotel.getId(),
            hotel.getName(),
            hotel.getDescription(),
            hotel.getHotelBrand(),
            hotel.getAddress().getHouseNumber(),
            hotel.getAddress().getStreet(),
            hotel.getAddress().getCity(),
            hotel.getAddress().getCountry(),
            hotel.getAddress().getPostalCode(),
            hotel.getContacts().getPhone(),
            hotel.getContacts().getEmail(),
            hotel.getArrivalTime().getCheckIn(),
            hotel.getArrivalTime().getCheckOut()
        );
    }

    public final Hotel entityToBusiness(HotelEntity hotelEntity) {
        return new Hotel(
                hotelEntity.getId(),
                hotelEntity.getName(),
                hotelEntity.getDescription(),
                hotelEntity.getBrand(),
                new Address(hotelEntity.getPostCode(),
                        hotelEntity.getCountry(),
                        hotelEntity.getCity(),
                        hotelEntity.getStreet(),
                        hotelEntity.getHouseNumber()),
                new Contacts(hotelEntity.getPhone(),
                        hotelEntity.getEmail()),
                new ArrivalTime(hotelEntity.getCheckIn(),
                        hotelEntity.getCheckOut())
        );
    }

    public final ExtendHotelResponseDto entityToExtendedResponseDto(HotelEntity hotelEntity) {
        return new ExtendHotelResponseDto(
                hotelEntity.getId(),
                hotelEntity.getName(),
                hotelEntity.getDescription(),
                hotelEntity.getBrand(),
                new Address(hotelEntity.getPostCode(),
                        hotelEntity.getCountry(),
                        hotelEntity.getCity(),
                        hotelEntity.getStreet(),
                        hotelEntity.getHouseNumber()),
                new Contacts(hotelEntity.getPhone(),
                        hotelEntity.getEmail()),
                new ArrivalTime(hotelEntity.getCheckIn(),
                        hotelEntity.getCheckOut()),
                hotelEntity.getAmenities()
        );
    }
}
