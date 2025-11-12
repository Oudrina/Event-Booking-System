package com.events.eventsbooking.booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBookingServiceRequest {
    private  int tickets;
    private Long eventId;
}
