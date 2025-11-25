package com.events.eventsbooking.booking;

import com.events.eventsbooking.event.Event;
import com.events.eventsbooking.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private int tickets;
    @Column(nullable = false)
    private double totalPrice;
    private LocalDateTime bookingDate;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "loggedInUser_id")
    private User user;

    @PrePersist
    protected void createdBookingDate() {
        LocalDateTime now = LocalDateTime.now();
        bookingDate = now;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return tickets == booking.tickets && Double.compare(totalPrice, booking.totalPrice) == 0 && Objects.equals(id, booking.id) && Objects.equals(bookingDate, booking.bookingDate) && Objects.equals(event, booking.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tickets, totalPrice, bookingDate, event);
    }
}
