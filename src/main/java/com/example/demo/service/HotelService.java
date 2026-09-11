package com.example.demo.service;

import com.example.demo.controller.HotelRequestDto;
import com.example.demo.mapper.Mapper;
import com.example.demo.repository.HotelEntity;
import com.example.demo.repository.HotelRepository;
import org.springframework.stereotype.Service;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;
    private final Mapper mapper;

    public HotelService(HotelRepository hotelRepository, Mapper mapper) {
        this.hotelRepository = hotelRepository;
        this.mapper = mapper;
    }

    public Hotel createHotel(Hotel hotelToCreate) {
        HotelEntity hotelEntity=hotelRepository.save(mapper.businessToEntity(hotelToCreate));
        return mapper.entityToBusiness(hotelEntity);
    }
}
