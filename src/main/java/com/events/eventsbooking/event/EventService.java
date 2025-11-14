package com.events.eventsbooking.event;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EventService {
    public Event findById(Long id);

    public List<Event> findAll();

//    public Event save(CreateEventServiceRequest event, MultipartFile imageFile) throws IOException;

    public void deleteEvent(Long id);
    public Event updateEvent( Long id , CreateEventServiceRequest event, MultipartFile imageFile) throws IOException;

    Event save(CreateEventServiceRequest event, MultipartFile imageFile, Authentication loggedInUser) throws IOException;
}
