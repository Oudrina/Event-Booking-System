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
public class EventDto {
    private Long id;
    private String title;
    private String description;
    private String category;
    private double price;
    private String location;
    private int capacity;
    private String user;
    private int availabileCapacity;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    private String imageUrl;


}