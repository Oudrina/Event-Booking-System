package com.events.eventsbooking.booking;

import org.springframework.security.core.Authentication;

import java.util.List;

public interface BookingService {
    List<Booking> getBookings();

    Booking getBookingByID(Long id);

//    Booking createBooking(CreateBookingServiceRequest booking);

    Booking updateBooking(Long id, CreateBookingServiceRequest booking);

    void deleteBooking(Long id);

//    Booking createBooking(CreateBookingServiceRequest booking, UserDetails userDetails);

    Booking createBooking(CreateBookingServiceRequest booking, Authentication principal);
}
