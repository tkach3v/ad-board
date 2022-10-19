package com.tkachev.adboard.services;

import com.tkachev.adboard.config.TestConfig;
import com.tkachev.adboard.dto.models.category.CreateCategoryRequest;
import com.tkachev.adboard.dto.models.category.UpdateCategoryRequest;
import com.tkachev.adboard.dto.models.user.CreateUserRequest;
import com.tkachev.adboard.dto.models.user.UpdateUserRequest;
import com.tkachev.adboard.dto.models.user.UserResponse;
import com.tkachev.adboard.entity.Category;
import com.tkachev.adboard.entity.Role;
import com.tkachev.adboard.entity.User;
import com.tkachev.adboard.entity.UserStatus;
import com.tkachev.adboard.exception_handling.exceptions.NoSuchEntityException;
import com.tkachev.adboard.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void beforeEach() {
        reset(categoryRepository);
    }

    @Test
    void callSaveCategoryWhenCreatingCategory() {
        CreateCategoryRequest dto = new CreateCategoryRequest();

        categoryService.createCategory(dto);

        verify(categoryRepository).save(any(Category.class));
    }


    @Test
    void checkCategoryExistenceAndCallDeleteCategoryWhenDeletingCategory() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(new Category()));

        categoryService.deleteCategory(anyLong());

        verify(categoryRepository).findById(anyLong());
        verify(categoryRepository).delete(any(Category.class));
    }

    @Test
    void throwExceptionIfCategoryDoesNotExistWhenDeletingCategory() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchEntityException.class, () -> categoryService.deleteCategory(anyLong()));
    }

    @Test
    void checkCategoryExistenceAndSaveCategoryWhenUpdatingCategory() {
        UpdateCategoryRequest dto = new UpdateCategoryRequest();
        dto.setId(1L);
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(new Category()));

        categoryService.updateCategory(dto);

        verify(categoryRepository).findById(anyLong());
        verify(categoryRepository).save(any(Category.class));
    }


    @Test
    void throwExceptionIfCategoryDoesNotExistWhenUpdatingCategory() {
        UpdateCategoryRequest dto = new UpdateCategoryRequest();
        dto.setId(1L);
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchEntityException.class, () -> categoryService.updateCategory(dto));
    }

    @Test
    void callFindAllWhenGettingCategories() {
        categoryService.getCategories();

        verify(categoryRepository).findAll();
    }

    @Test
    void checkCategoryExistenceWhenGettingCategoryById() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(new Category()));

        categoryService.getCategoryById(anyLong());

        verify(categoryRepository).findById(anyLong());
    }

    @Test
    void throwExceptionIfCategoryDoesNotExistWhenGettingCategoryById() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchEntityException.class, () -> categoryService.getCategoryById(anyLong()));
    }
}