package com.tkachev.adboard.rest.controllers;

import com.tkachev.adboard.dto.models.category.CategoryResponse;
import com.tkachev.adboard.dto.models.category.CreateCategoryRequest;
import com.tkachev.adboard.dto.models.category.UpdateCategoryRequest;
import com.tkachev.adboard.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @PreAuthorize("hasAuthority('categories:read')")
    public ResponseEntity<List<CategoryResponse>> getCategories() {
        List<CategoryResponse> categories = categoryService.getCategories();

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('categories:write')")
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CreateCategoryRequest dto) {
        CategoryResponse category = categoryService.createCategory(dto);

        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('categories:read')")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable(name = "id") Long id) {
        CategoryResponse category = categoryService.getCategoryById(id);

        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('categories:write')")
    public ResponseEntity<CategoryResponse> updateCategory(@Valid @RequestBody UpdateCategoryRequest dto) {
        CategoryResponse category = categoryService.updateCategory(dto);

        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('categories:delete')")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);

        return new ResponseEntity<>("Category with ID = " + id + " has been deleted", HttpStatus.NO_CONTENT);
    }
}
