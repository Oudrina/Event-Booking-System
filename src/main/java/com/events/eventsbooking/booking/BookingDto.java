package com.events.eventsbooking.booking;


import java.time.LocalDateTime;


public record BookingDto(
        Long id,
        int tickets,
        String eventTitle,
        String user,
        double totalPrice,
        LocalDateTime bookingDate
) {
}
