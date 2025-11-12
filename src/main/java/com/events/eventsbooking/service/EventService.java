package com.events.eventsbooking.service;

import com.events.eventsbooking.event.CreateEventServiceRequest;
import com.events.eventsbooking.event.Event;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EventService {
    public Event findById(Long id);

    public List<Event> findAll();

    public Event save(CreateEventServiceRequest event, MultipartFile imageFile) throws IOException;

    public void deleteEvent(Long id);
    public Event updateEvent( Long id , CreateEventServiceRequest event, MultipartFile imageFile) throws IOException;

}
