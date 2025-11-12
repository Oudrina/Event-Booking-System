package com.events.eventsbooking.service.ServiceImpl;

import com.events.eventsbooking.booking.CreateBookingServiceRequest;
import com.events.eventsbooking.booking.Booking;

import com.events.eventsbooking.event.Event;
import com.events.eventsbooking.booking.BookingRepo;
import com.events.eventsbooking.service.BookingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepo bookingRepo;
    private final EventServiceImpl eventService;

    @Override
    public List<Booking> getBookings() {
        return bookingRepo.findAll();
    }

    @Override
    public Booking getBookingByID(Long id) {
        return bookingRepo.getBookingById(id);
    }

    @Override
    public Booking createBooking(CreateBookingServiceRequest booking) {
     Booking creatBooking = new Booking();

        Event event = eventService.findById(booking.getEventId());
        if (event != null) {
            creatBooking.setEvent(event);

        } else throw new EntityNotFoundException("Event not found");


//        Check for capacity availability
        if (creatBooking.getTickets() > event.getAvailabileCapacity()) {
            throw new RuntimeException("Capacity exceeded");
        } else {
            event.setAvailabileCapacity(event.getCapacity() - booking.getTickets());
        }
//        calculate the total price

        double totalPrice = booking.getTickets() * event.getPrice();

        creatBooking.setTotalPrice(totalPrice);

        creatBooking.setTickets(booking.getTickets());
        return bookingRepo.save(creatBooking);

    }


    @Override
    public Booking updateBooking(Long id,   CreateBookingServiceRequest  booking) {
        Booking existingBooking = bookingRepo.getBookingById(booking.getEventId());
            Event updatedEvent = eventService.findById(booking.getEventId());
        existingBooking.setEvent(updatedEvent);
        existingBooking.setTickets(booking.getTickets());
        existingBooking.setTotalPrice(booking.getTickets() * updatedEvent.getPrice());


        return   bookingRepo.save(existingBooking);
    }

    @Override
    public void deleteBooking(Long id) {
        Booking existingBooking = bookingRepo.getBookingById(id);
        if (existingBooking != null) {
            bookingRepo.delete(existingBooking);
        }
       throw  new RuntimeException("Booking not found");
    }

}
