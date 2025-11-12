package com.events.eventsbooking.service.ServiceImpl;

import com.events.eventsbooking.event.CreateEventServiceRequest;
import com.events.eventsbooking.category.Category;
import com.events.eventsbooking.event.Event;
import com.events.eventsbooking.event.EventRepo;
import com.events.eventsbooking.service.CategoryService;
import com.events.eventsbooking.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepo eventRepo;
    private final CategoryService categoryService;
    private final CloudinaryServiceImpl cloudinaryService;

    @Override
    public Event findById(Long id) {
        Event event = eventRepo.findById(id).
                orElseThrow();
        return event;
    }


    @Override
    public List<Event> findAll() {
        return eventRepo.findAll();
    }

    @Override
    @Transactional
    public Event save(CreateEventServiceRequest event, MultipartFile imageFile) throws IOException {

        Category category = categoryService.getCategoryById(event.getCategoryId());
//        if (category != null) {
//            createdEvent.setCategory(category);
//        } else throw new RuntimeException("Category not found or invalid");
        Map<String, String> imageUrl = cloudinaryService.uploadImage(imageFile);

//        if (!imageFile.isEmpty()) {
//            createdEvent.setImagePublicId(imageUrl.get("publicId"));
//            createdEvent.setImageUrl(imageUrl.get("url"));
//        } else throw new RuntimeException("Image not found");



        Event createdEvent = Event
                .builder()
                .title(event.getTitle())
                .description(event.getDescription())
                .price(event.getPrice())
                .location(event.getLocation())
                .date(event.getDate())
                .availabileCapacity(event.getCapacity())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .capacity(event.getCapacity())
                .category(category)
                .imagePublicId(imageUrl.get("publicId"))
                .imageUrl(imageUrl.get("url"))
                .build();




        return eventRepo.save(createdEvent);
    }

    @Override
    public void deleteEvent(Long id) {
        Event event = eventRepo.findById(id).orElseThrow();
        if (event.getImagePublicId() != null) {
            try {
                cloudinaryService.deleteImage(event.getImagePublicId());
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        eventRepo.delete(event);


    }

    @Override
    public Event updateEvent(Long id, CreateEventServiceRequest event, MultipartFile imageFile) throws IOException {

        Event existingEvent = eventRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Event not found"));

            existingEvent.setTitle(event.getTitle());
            existingEvent.setDescription(event.getDescription());
            existingEvent.setPrice(event.getPrice());
            existingEvent.setLocation(event.getLocation());
            existingEvent.setDate(event.getDate());
            existingEvent.setEndTime(event.getEndTime());
            existingEvent.setStartTime(event.getStartTime());
            existingEvent.setCapacity(event.getCapacity());
            existingEvent.setAvailabileCapacity(event.getCapacity());
            Category category = categoryService.getCategoryById(event.getCategoryId());
            existingEvent.setCategory(category);

        if (imageFile != null && !imageFile.isEmpty()) {
            if (existingEvent.getImagePublicId() != null) {
                cloudinaryService.deleteImage(existingEvent.getImagePublicId());
            }
            Map<String, String> imageUrl = cloudinaryService.uploadImage(imageFile);

            existingEvent.setImagePublicId(imageUrl.get("public_id"));
            existingEvent.setImageUrl(imageUrl.get("url"));
        }

        return eventRepo.save(existingEvent);
    }


}
