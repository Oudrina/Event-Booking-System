package com.events.eventsbooking.event;

import java.time.LocalDate;
import java.time.LocalTime;


public record EventDto(
        Long id,
        String title,
        String description,
        String category,
        double price,
        String location,
        int capacity,
        String user,
        int availabileCapacity,
        LocalTime startTime,
        LocalTime endTime,
        LocalDate date,
        String imageUrl
) {
}