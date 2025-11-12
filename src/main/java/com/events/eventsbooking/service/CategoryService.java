package com.events.eventsbooking.service;

import com.events.eventsbooking.category.Category;

import java.util.List;

public interface CategoryService {
    public List<Category> getCategories();
    public Category getCategoryById(Long id);
    public Category createCategory(Category category);
    public Category updateCategory( Long id,Category category);
    public void deleteCategory(Long id);
}
