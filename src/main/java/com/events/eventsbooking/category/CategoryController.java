package com.events.eventsbooking.category;

import com.events.eventsbooking.mappers.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryServiceImpl categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categoryDtos = categoryService.getCategories()
                .stream().map(categoryMapper::toCategoryDto).toList();
        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CreateCategoryRequest request) {

        Category createdCategoryRequest = categoryMapper.toEntity(request);
        Category category = categoryService.createCategory(createdCategoryRequest);

        CategoryDto categoryDto = categoryMapper.toCategoryDto(category);

        return new ResponseEntity<>(categoryDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return new ResponseEntity<>(categoryService.getCategoryById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody CreateCategoryRequest request) {
        Category updatedCategoryRequest = categoryMapper.toEntity(request);
        Category category = categoryService.updateCategory(id, updatedCategoryRequest);
        CategoryDto categoryDto = categoryMapper.toCategoryDto(category);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
