package com.tkachev.adboard.services.impl;

import com.tkachev.adboard.dto.models.category.CategoryResponse;
import com.tkachev.adboard.dto.models.category.CreateCategoryRequest;
import com.tkachev.adboard.dto.models.category.UpdateCategoryRequest;
import com.tkachev.adboard.entity.Category;
import com.tkachev.adboard.dto.mappers.CategoryMapper;
import com.tkachev.adboard.repositories.CategoryRepository;
import com.tkachev.adboard.services.AbstractService;
import com.tkachev.adboard.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends AbstractService implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createCategory(CreateCategoryRequest dto) {
        Category category = categoryMapper.toCategory(dto);
        categoryRepository.save(category);

        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        category = isNotNull(category, "Category", id);
        categoryRepository.delete(category);
    }

    @Override
    public CategoryResponse updateCategory(UpdateCategoryRequest dto) {
        Category category = categoryRepository.findById(dto.getId()).orElse(null);
        category = isNotNull(category, "Category", dto.getId());
        categoryMapper.updateCategory(dto, category);
        categoryRepository.save(category);

        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAll().
                stream().
                map(categoryMapper::toCategoryResponse).
                toList();
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        category = isNotNull(category, "Category", id);

        return categoryMapper.toCategoryResponse(category);
    }
}
