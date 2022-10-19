package com.tkachev.adboard.services;

import com.tkachev.adboard.dto.models.review.CreateReviewRequest;
import com.tkachev.adboard.dto.models.review.ReviewResponse;
import com.tkachev.adboard.dto.models.review.UpdateReviewRequest;

import java.util.List;

public interface ReviewService {

    ReviewResponse createReview(CreateReviewRequest dto);

    void deleteReview(Long id);

    ReviewResponse updateReview(UpdateReviewRequest dto);

    List<ReviewResponse> getReviews();

    ReviewResponse getReviewById(Long id);
}
