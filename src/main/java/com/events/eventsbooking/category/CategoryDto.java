package com.events.eventsbooking.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public record CategoryDto(
        Long id,
        String name
) {
}
