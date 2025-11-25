package com.events.eventsbooking.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateEventServiceRequest {
    private String title;
    private String description;
    private Long categoryId;
    private int capacity;
    private double price;
    private String location;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    private String imageUrl;
}
