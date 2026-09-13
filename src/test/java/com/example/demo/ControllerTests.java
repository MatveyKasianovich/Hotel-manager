package com.example.demo;

import com.example.demo.controller.HotelController;
import com.example.demo.controller.HotelRequestDto;
import com.example.demo.controller.HotelResponseDto;
import com.example.demo.dto.Address;
import com.example.demo.dto.ArrivalTime;
import com.example.demo.dto.Contacts;
import com.example.demo.mapper.Mapper;
import com.example.demo.service.Hotel;
import com.example.demo.service.HotelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HotelController.class)
class ControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private Mapper mapper;

    @MockitoBean
    private HotelService hotelService;

    private HotelRequestDto request;
    private HotelRequestDto badRequest;
    private Hotel mappedFromDto;
    private Hotel createdHotel;
    private HotelResponseDto responseDto;

    @BeforeEach
    void setUp() {
        request = new HotelRequestDto(
                "Grand Plaza",
                "A hotel in the city center",
                "Hilton",
                new Address("10115", "Germany", "Berlin", "Unter den Linden", 1L),
                new Contacts("+49301234567", "info@grandplaza.com"),
                new ArrivalTime(
                        LocalDateTime.now().plusDays(1),
                        LocalDateTime.now().plusDays(2)
                )
        );

        mappedFromDto = new Hotel(
                request.getName(),
                request.getDescription(),
                request.getHotelBrand(),
                request.getAddress(),
                request.getContacts(),
                request.getArrivalTime()
        );

        createdHotel = new Hotel(
                42L,
                request.getName(),
                request.getDescription(),
                request.getHotelBrand(),
                request.getAddress(),
                request.getContacts(),
                request.getArrivalTime()
        );

        responseDto = new HotelResponseDto(
                42L,
                "Grand Plaza",
                "A hotel in the city center",
                "1 Unter den Linden, Berlin, 10115, Germany",
                "+49301234567"
        );

        badRequest = new HotelRequestDto(
                "Grand Plaza",
                "A hotel in the city center",
                "Hilton",
                new Address("10115", "Germany", "Berlin", "Unter den Linden", 1L),
                new Contacts("+49301234567", "info@grandplaza.com"),
                new ArrivalTime(
                        LocalDateTime.now().minusDays(1),
                        LocalDateTime.now().plusDays(2)
                )
        );
    }

    @Test
    void createHotel_withValidBody_returnsCreatedWithBody() throws Exception {

        when(mapper.requestDtoToBusiness(any(HotelRequestDto.class))).thenReturn(mappedFromDto);
        when(hotelService.createHotel(mappedFromDto)).thenReturn(createdHotel);
        when(mapper.businessToResponseDto(createdHotel)).thenReturn(responseDto);

        mockMvc.perform(post("/property-view/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(42))
                .andExpect(jsonPath("$.name").value("Grand Plaza"))
                .andExpect(jsonPath("$.phone").value("+49301234567"));
    }

    @Test
    void createHotel_withInvalidCheckInDate_returnsBadRequestWithValidationMessage() throws Exception {

        mockMvc.perform(post("/property-view/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("validation error"))
                .andExpect(jsonPath("$.detailedMessage").value(
                        org.hamcrest.Matchers.containsString("checkIn")));

        org.mockito.Mockito.verifyNoInteractions(hotelService);
        org.mockito.Mockito.verifyNoInteractions(mapper);
    }

    @Test
    void createHotel_withMissingRequiredFields_returnsBadRequestListingAllViolations() throws Exception {

        HotelRequestDto emptyRequest = new HotelRequestDto(
                "",
                "A hotel in the city center",
                "",
                new Address(null, null, null, null, null),
                new Contacts(null, "not-an-email"),
                new ArrivalTime(
                        LocalDateTime.now().plusDays(1),
                        LocalDateTime.now().plusDays(2)
                )
        );

        mockMvc.perform(post("/property-view/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emptyRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("validation error"))
                .andExpect(jsonPath("$.detailedMessage").value(
                        org.hamcrest.Matchers.containsString("phone")))
                .andExpect(jsonPath("$.detailedMessage").value(
                        org.hamcrest.Matchers.containsString("email")));
    }
}