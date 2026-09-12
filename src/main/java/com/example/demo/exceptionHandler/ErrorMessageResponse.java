package com.example.demo.exceptionHandler;

import java.time.LocalDateTime;

public record ErrorMessageResponse(
        String message,
        String detailedMessage,
        LocalDateTime localDateTime
) {
}
