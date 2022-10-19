package com.tkachev.adboard.services;

import com.tkachev.adboard.dto.models.category.CategoryResponse;
import com.tkachev.adboard.dto.models.category.CreateCategoryRequest;
import com.tkachev.adboard.dto.models.category.UpdateCategoryRequest;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CreateCategoryRequest dto);

    void deleteCategory(Long id);

    CategoryResponse updateCategory(UpdateCategoryRequest dto);

    List<CategoryResponse> getCategories();

    CategoryResponse getCategoryById(Long id);
}
