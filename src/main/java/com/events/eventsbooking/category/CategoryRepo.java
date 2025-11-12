package com.events.eventsbooking.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {
    List<Category> id(Long id);

    Category getCategoryById(Long id);
}
