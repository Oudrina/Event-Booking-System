package com.events.eventsbooking.mappers;

import com.events.eventsbooking.booking.CreateBookingRequestDto;
import com.events.eventsbooking.booking.CreateBookingServiceRequest;
import com.events.eventsbooking.booking.BookingDto;
import com.events.eventsbooking.booking.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    @Mapping(target = "eventTitle" ,source = "event.title")
    @Mapping(target = "user", source = "user.username")
    BookingDto toBookingDto(Booking booking);

//    @Mapping(target = "eventId" ,source = "event.id")
    CreateBookingServiceRequest toEntity(CreateBookingRequestDto request);

}
