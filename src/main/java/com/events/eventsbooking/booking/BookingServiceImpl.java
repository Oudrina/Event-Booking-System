package com.events.eventsbooking.booking;

import com.events.eventsbooking.auth.AuthenticationService;

import com.events.eventsbooking.event.Event;
import com.events.eventsbooking.event.EventServiceImpl;
import com.events.eventsbooking.user.User;
import com.events.eventsbooking.user.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepo bookingRepo;
    private final EventServiceImpl eventService;
    private final AuthenticationService authenticationService;
    private final UserRepo userRepo;

    @Override
    public List<Booking> getBookings() {
        return bookingRepo.findAll();
    }

    @Override
    public Booking getBookingByID(Long id) {
        return bookingRepo.getBookingById(id);
    }

    @Override
    public Booking createBooking(CreateBookingServiceRequest booking , Authentication principal) {
    User user = (User) principal.getPrincipal();


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
        creatBooking.setUser(user);
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
