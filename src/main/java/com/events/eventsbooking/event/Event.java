package com.events.eventsbooking.event;

import com.events.eventsbooking.booking.Booking;
import com.events.eventsbooking.category.Category;
import com.events.eventsbooking.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private int availabileCapacity;

    @Column(nullable = false)
    @JsonFormat( shape = JsonFormat.Shape.STRING ,pattern = "HH:mm")
    private LocalTime startTime;

    @Column(nullable = false)
    @JsonFormat( shape = JsonFormat.Shape.STRING ,pattern = "HH:mm")
    private LocalTime endTime;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING ,pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String  imageUrl;
    private String imagePublicId;



    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy ="event" ,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private List<Booking> bookings = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Double.compare(price, event.price) == 0 && capacity == event.capacity && availabileCapacity == event.availabileCapacity && Objects.equals(id, event.id) && Objects.equals(title, event.title) && Objects.equals(description, event.description) && Objects.equals(location, event.location) && Objects.equals(startTime, event.startTime) && Objects.equals(endTime, event.endTime) && Objects.equals(date, event.date) && Objects.equals(imageUrl, event.imageUrl) && Objects.equals(imagePublicId, event.imagePublicId) && Objects.equals(category, event.category) && Objects.equals(bookings, event.bookings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, price, location, capacity, availabileCapacity, startTime, endTime, date, imageUrl, imagePublicId, category, bookings);
    }
}

