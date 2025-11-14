package com.events.eventsbooking.mappers;

import com.events.eventsbooking.event.CreateEventRequestDto;
import com.events.eventsbooking.event.CreateEventServiceRequest;
import com.events.eventsbooking.event.EventDto;
import com.events.eventsbooking.event.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(target = "category" ,source = "category.name")
    @Mapping(target = "user", source = "user.username")
    EventDto toDto(Event event);
        CreateEventServiceRequest toEntity(CreateEventRequestDto request);
}
