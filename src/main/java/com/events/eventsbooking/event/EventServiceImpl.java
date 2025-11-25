package com.events.eventsbooking.event;

import com.events.eventsbooking.category.Category;
import com.events.eventsbooking.category.CategoryServiceImpl;
import com.events.eventsbooking.cloudinary.CloudinaryServiceImpl;
import com.events.eventsbooking.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventServiceImpl {
    private final EventRepo eventRepo;
    private final CategoryServiceImpl categoryService;
    private final CloudinaryServiceImpl cloudinaryService;


    public Event findById(Long id) {
        return eventRepo.findById(id).
                orElseThrow(()-> new UsernameNotFoundException("Username with id"+id+"not found "));
    }

    public List<Event> findAll() {
        return eventRepo.findAll();
    }


    @Transactional
    public Event save(CreateEventServiceRequest event, MultipartFile imageFile, Authentication loggedInUser) throws IOException {
        User user = (User) loggedInUser.getPrincipal();

        Category category = categoryService.getCategoryById(event.getCategoryId());

        Map<String, String> imageUrl = cloudinaryService.uploadImage(imageFile);
        
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
                .user(user)
                .build();


        return eventRepo.save(createdEvent);
    }


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


    public Event updateEvent(Long id, CreateEventServiceRequest event, MultipartFile imageFile) throws IOException {

        Event existingEvent = eventRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

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
