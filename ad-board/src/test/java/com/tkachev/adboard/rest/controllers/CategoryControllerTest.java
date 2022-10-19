package com.tkachev.adboard.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tkachev.adboard.dto.models.category.CategoryResponse;
import com.tkachev.adboard.dto.models.category.CreateCategoryRequest;
import com.tkachev.adboard.dto.models.category.UpdateCategoryRequest;
import com.tkachev.adboard.dto.models.user.CreateUserRequest;
import com.tkachev.adboard.dto.models.user.UpdateUserRequest;
import com.tkachev.adboard.dto.models.user.UserResponse;
import com.tkachev.adboard.security.JwtTokenProvider;
import com.tkachev.adboard.services.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String url = "/api/v1/categories";

    @Test
    void shouldCreateCategory() throws Exception {
        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest();
        createCategoryRequest.setTitle("books");
        when(categoryService.createCategory(createCategoryRequest)).thenReturn(new CategoryResponse());

        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCategoryRequest)))
                .andExpect(status().isCreated())
                .andDo(print());

        verify(categoryService).createCategory(createCategoryRequest);
    }

    @Test
    void shouldUpdateCategory() throws Exception {
        UpdateCategoryRequest updateCategoryRequest = new UpdateCategoryRequest();
        updateCategoryRequest.setId(1L);
        updateCategoryRequest.setTitle("cars");
        when(categoryService.updateCategory(updateCategoryRequest)).thenReturn(new CategoryResponse());

        mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateCategoryRequest)))
                .andExpect(status().isOk())
                .andDo(print());

        verify(categoryService).updateCategory(updateCategoryRequest);
    }

    @Test
    void shouldGetCategories() throws Exception {
        when(categoryService.getCategories()).thenReturn(new ArrayList<>());

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andDo(print());

        verify(categoryService).getCategories();
    }

    @Test
    void shouldGetCategoryById() throws Exception {
        Long categoryId = 1L;
        when(categoryService.getCategoryById(categoryId)).thenReturn(new CategoryResponse());

        mockMvc.perform(get(url + "/" + categoryId))
                .andExpect(status().isOk())
                .andDo(print());

        verify(categoryService).getCategoryById(categoryId);
    }

    @Test
    void shouldDeleteCategory() throws Exception {
        Long categoryId = 1L;

        mockMvc.perform(delete(url + "/" + categoryId))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(categoryService).deleteCategory(categoryId);
    }
}