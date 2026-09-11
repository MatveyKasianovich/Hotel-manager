package com.example.demo.dto;

import jakarta.validation.constraints.Future;

import java.time.LocalDateTime;

public class ArrivalTime {

    @Future(message = "check in should be in future")
    private LocalDateTime checkIn;

    @Future(message = "check out should be in future")
    private LocalDateTime checkOut;

    public ArrivalTime(LocalDateTime checkIn, LocalDateTime checkOut) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }
}
