package com.tkachev.adboard.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tkachev.adboard.dto.models.review.CreateReviewRequest;
import com.tkachev.adboard.dto.models.review.ReviewResponse;
import com.tkachev.adboard.dto.models.review.UpdateReviewRequest;
import com.tkachev.adboard.dto.models.user.CreateUserRequest;
import com.tkachev.adboard.dto.models.user.UpdateUserRequest;
import com.tkachev.adboard.dto.models.user.UserResponse;
import com.tkachev.adboard.security.JwtTokenProvider;
import com.tkachev.adboard.services.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @MockBean
    private ReviewService reviewService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String url = "/api/v1/reviews";

    @Test
    void shouldCreateReview() throws Exception {
        CreateReviewRequest createReviewRequest = new CreateReviewRequest();
        createReviewRequest.setScore((short) 5);
        createReviewRequest.setComment("It was great!");
        createReviewRequest.setAuthorId(1L);
        createReviewRequest.setAdId(1L);
        when(reviewService.createReview(createReviewRequest)).thenReturn(new ReviewResponse());

        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createReviewRequest)))
                .andExpect(status().isCreated())
                .andDo(print());

        verify(reviewService).createReview(any(CreateReviewRequest.class));
    }

    @Test
    void shouldUpdateReview() throws Exception {
        UpdateReviewRequest updateReviewRequest = new UpdateReviewRequest();
        updateReviewRequest.setId(1L);
        updateReviewRequest.setScore((short) 5);
        updateReviewRequest.setComment("It was great!");
        updateReviewRequest.setAuthorId(1L);
        updateReviewRequest.setAdId(1L);
        when(reviewService.updateReview(updateReviewRequest)).thenReturn(new ReviewResponse());

        mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateReviewRequest)))
                .andExpect(status().isOk())
                .andDo(print());

        verify(reviewService).updateReview(updateReviewRequest);
    }

    @Test
    void shouldGetReviews() throws Exception {
        when(reviewService.getReviews()).thenReturn(new ArrayList<>());

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andDo(print());

        verify(reviewService).getReviews();
    }

    @Test
    void shouldGetReviewById() throws Exception {
        Long reviewId = 1L;
        when(reviewService.getReviewById(reviewId)).thenReturn(new ReviewResponse());

        mockMvc.perform(get(url + "/" + reviewId))
                .andExpect(status().isOk())
                .andDo(print());

        verify(reviewService).getReviewById(reviewId);
    }

    @Test
    void shouldDeleteReview() throws Exception {
        Long reviewId = 1L;

        mockMvc.perform(delete(url + "/" + reviewId))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(reviewService).deleteReview(reviewId);
    }
}