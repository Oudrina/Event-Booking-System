package com.events.eventsbooking.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl  {
    private final CategoryRepo categoryRepo;

    public List<Category> getCategories() {
        return categoryRepo.findAll();

    }

    public Category getCategoryById(Long id) {

        return categoryRepo.getCategoryById(id);
    }


    public Category createCategory(Category category) {
        return categoryRepo.save(category);

    }


    public Category updateCategory(Long id, Category category) {
        Category updatedCategory = categoryRepo.getCategoryById(id);
        updatedCategory.setName(category.getName());
        return categoryRepo.save(updatedCategory);

    }

    public void deleteCategory(Long id) {
        categoryRepo.deleteById(id);
    }
}
