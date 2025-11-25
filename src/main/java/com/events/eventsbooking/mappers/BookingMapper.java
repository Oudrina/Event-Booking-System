package com.events.eventsbooking.mappers;

import com.events.eventsbooking.booking.Booking;
import com.events.eventsbooking.booking.BookingDto;
import com.events.eventsbooking.booking.CreateBookingRequestDto;
import com.events.eventsbooking.booking.CreateBookingServiceRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    @Mapping(target = "eventTitle", source = "event.title")
    @Mapping(target = "user", source = "user.username")
    BookingDto toBookingDto(Booking booking);

    CreateBookingServiceRequest toEntity(CreateBookingRequestDto request);

}
