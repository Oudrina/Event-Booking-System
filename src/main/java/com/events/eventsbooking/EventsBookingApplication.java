package com.events.eventsbooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EventsBookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventsBookingApplication.class, args);
    }

}
