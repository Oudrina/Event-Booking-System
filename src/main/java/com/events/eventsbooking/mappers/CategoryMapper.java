package com.events.eventsbooking.mappers;

import com.events.eventsbooking.category.Category;
import com.events.eventsbooking.category.CategoryDto;
import com.events.eventsbooking.category.CreateCategoryRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toCategoryDto(Category category);

    Category toEntity(CreateCategoryRequest request);
}
