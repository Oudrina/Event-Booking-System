package com.events.eventsbooking.booking;

import com.events.eventsbooking.mappers.BookingMapper;
import com.events.eventsbooking.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private  final BookingMapper bookingMapper;
    private final UserRepo userRepo;

    @GetMapping
    public  ResponseEntity<List<BookingDto>> getBookings(){
        List<BookingDto> bookings = bookingService.getBookings()
                .stream()
                .map(bookingMapper::toBookingDto).toList();

        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> getBookingByID(@PathVariable Long id) {

        Booking booking = bookingService.getBookingByID(id);
        BookingDto bookingDto = bookingMapper.toBookingDto(booking);

        return new ResponseEntity<>(bookingDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookingDto> createBooking(@RequestBody CreateBookingRequestDto request ,
                                                    Authentication principal){


        CreateBookingServiceRequest booking = bookingMapper.toEntity(request);
        Booking createdBooking = bookingService.createBooking(booking ,principal);
        BookingDto bookingDto = bookingMapper.toBookingDto(createdBooking);

        return new ResponseEntity<>(bookingDto,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        bookingService.deleteBooking(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingDto> updateBooking(@PathVariable Long id, @RequestBody CreateBookingRequestDto booking){
        CreateBookingServiceRequest bookingUpdate = bookingMapper.toEntity(booking);

        Booking updatedBooking = bookingService.updateBooking(id, bookingUpdate);
        BookingDto bookingDto = bookingMapper.toBookingDto(updatedBooking);

        return new ResponseEntity<>(bookingDto, HttpStatus.OK);
    }
}

