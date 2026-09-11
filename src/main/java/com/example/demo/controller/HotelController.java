package com.example.demo.controller;

import com.example.demo.mapper.Mapper;
import com.example.demo.service.Hotel;
import com.example.demo.service.HotelService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/property-view")
public class HotelController {

    private final Mapper mapper;
    private final HotelService hotelService;

    public HotelController(Mapper mapper, HotelService hotelService) {
        this.mapper = mapper;
        this.hotelService = hotelService;
    }

    @PostMapping("/hotels")
    public ResponseEntity<HotelResponseDto> createHotel(@RequestBody @Valid HotelRequestDto hotelRequestDto) {
        Hotel hotelToCreate=mapper.requestDtoToBusiness(hotelRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.businessToResponseDto(hotelService.createHotel(hotelToCreate)));
    }
}
