package com.events.eventsbooking.booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BookingDto {
    private Long id;
    private  int tickets;
    private String eventTitle;
    private  String user;
    private double totalPrice;
    private LocalDateTime bookingDate;
}
