package com.events.eventsbooking.event;

import com.events.eventsbooking.mappers.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventServiceImpl eventService;
    private final EventMapper eventMapper;

    public EventController(EventServiceImpl eventService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }


    @GetMapping
    public ResponseEntity<List<EventDto>> findAll() {
        List<EventDto> eventList = eventService.findAll().stream()
                .map(eventMapper::toDto).toList();

        return ResponseEntity.ok(eventList);


    }

    @PostMapping
    public ResponseEntity<EventDto> save(@RequestPart("event") CreateEventRequestDto request,
                                         @RequestPart(required = false, value = "image") MultipartFile imageFile,
                                         Authentication loggedInUser
    ) {

        try {

            CreateEventServiceRequest event = eventMapper.toEntity(request);

            Event addEvent = eventService.save(event, imageFile, loggedInUser);
            EventDto eventDto = eventMapper.toDto(addEvent);

            if (eventDto == null) {
                new ResponseEntity<EventDto>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(eventDto, HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();

        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> findById(@PathVariable Long id) {
        Event event = eventService.findById(id);
        EventDto eventDto = eventMapper.toDto(event);

        return new ResponseEntity<>(eventDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDto> updateEvent(@PathVariable Long id,
                                                @RequestPart("event") CreateEventRequestDto request,
                                                @RequestPart("image") MultipartFile imageFile) throws IOException {
        CreateEventServiceRequest event = eventMapper.toEntity(request);
        Event updatedEvent = eventService.updateEvent(id, event, imageFile);
        EventDto eventDto = eventMapper.toDto(updatedEvent);
        return new ResponseEntity<>(eventDto, HttpStatus.OK);
    }

}
