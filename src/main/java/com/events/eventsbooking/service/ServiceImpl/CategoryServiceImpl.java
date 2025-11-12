package com.events.eventsbooking.service.ServiceImpl;

import com.events.eventsbooking.category.Category;
import com.events.eventsbooking.category.CategoryRepo;
import com.events.eventsbooking.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl  implements CategoryService {
    private  final CategoryRepo categoryRepo;
    @Override
    public List<Category> getCategories() {
       return categoryRepo.findAll();

    }

    @Override
    public Category getCategoryById(Long id) {

        return  categoryRepo.getCategoryById( id);
    }

    @Override
    public Category createCategory(Category category) {
       return categoryRepo.save(category);

    }

    @Override
    public Category updateCategory(Long id, Category category) {
        Category updatedCategory = categoryRepo.getCategoryById(id);
        updatedCategory.setName(category.getName());
        return categoryRepo.save(updatedCategory);

    }

    @Override
    public void deleteCategory(Long id) {
   categoryRepo.deleteById(id);
    }
}
