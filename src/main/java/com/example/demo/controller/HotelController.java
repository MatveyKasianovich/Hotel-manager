package com.example.demo.controller;

import com.example.demo.exceptionHandler.GlobalExceptionHandler;
import com.example.demo.mapper.Mapper;
import com.example.demo.service.Hotel;
import com.example.demo.service.HotelService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/property-view")
public class HotelController {

    private final Mapper mapper;
    private final HotelService hotelService;
    public static final Logger log = LoggerFactory.getLogger(HotelController.class);

    public HotelController(Mapper mapper, HotelService hotelService) {
        this.mapper = mapper;
        this.hotelService = hotelService;
    }

    @PostMapping("/hotels")
    public ResponseEntity<HotelResponseDto> createHotel(@RequestBody @Valid HotelRequestDto hotelRequestDto) {
        log.info("createHotel");
        Hotel hotelToCreate=mapper.requestDtoToBusiness(hotelRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.businessToResponseDto(hotelService.createHotel(hotelToCreate)));
    }

    @GetMapping("/hotels")
    public ResponseEntity<List<HotelResponseDto>> getAllHotels() {
        log.info("getAllHotels");
        List<Hotel> allHotels = hotelService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(allHotels.stream().map(mapper::businessToResponseDto).collect(Collectors.toUnmodifiableList()));
    }

    @GetMapping("/hotels/{id}")
    public ResponseEntity<ExtendHotelResponseDto> getHotelById(@PathVariable Long id) {
        log.info("getHotelById");
        return ResponseEntity.status(HttpStatus.OK).body(mapper.entityToExtendedResponseDto(hotelService.findHotelById(id)));
    }

    @GetMapping("/search")
    public ResponseEntity<List<HotelResponseDto>> searchByFilter(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) List<String> amenities) {
        log.info("searchByFilter");
        List<Hotel> foundHotels = hotelService.findHotelsByFilter(name, brand, city, country, amenities);
        return ResponseEntity.status(HttpStatus.OK)
                .body(foundHotels.stream().map(mapper::businessToResponseDto).collect(Collectors.toUnmodifiableList()));
    }

    @PostMapping("/hotels/{id}/amenities")
    public ResponseEntity<ExtendHotelResponseDto> addAmenityToHotel(@PathVariable Long id, @RequestBody List<String> amenities) {
        log.info("addAmenityToHotel");
        return ResponseEntity.status(HttpStatus.OK).body(mapper.entityToExtendedResponseDto(hotelService.addAmenityToHotel(id,amenities)));
    }

    @GetMapping("/histogram/{param}")
    public ResponseEntity<Map<String, Long>> histogram(@PathVariable String param) {
        log.info("histogram");
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.histogram(param));
    }
}
