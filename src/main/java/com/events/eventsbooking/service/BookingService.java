package com.events.eventsbooking.service;

import com.events.eventsbooking.booking.CreateBookingServiceRequest;
import com.events.eventsbooking.booking.Booking;

import java.util.List;

public interface BookingService {
    List<Booking> getBookings();

    Booking getBookingByID(Long id);

    Booking createBooking(CreateBookingServiceRequest booking);

    Booking updateBooking(Long id, CreateBookingServiceRequest booking);

    void deleteBooking(Long id);
}
