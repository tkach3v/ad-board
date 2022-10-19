package com.tkachev.adboard.rest.controllers;

import com.tkachev.adboard.dto.models.review.CreateReviewRequest;
import com.tkachev.adboard.dto.models.review.ReviewResponse;
import com.tkachev.adboard.dto.models.review.UpdateReviewRequest;
import com.tkachev.adboard.services.ReviewService;
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
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    @PreAuthorize("hasAuthority('reviews:read')")
    public ResponseEntity<List<ReviewResponse>> getReviews() {
        List<ReviewResponse> reviews = reviewService.getReviews();

        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('reviews:write')")
    public ResponseEntity<ReviewResponse> createReview(@Valid @RequestBody CreateReviewRequest dto) {
        ReviewResponse review = reviewService.createReview(dto);

        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('reviews:read')")
    public ResponseEntity<ReviewResponse> getReviewById(@PathVariable(name = "id") Long id) {
        ReviewResponse review = reviewService.getReviewById(id);

        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('reviews:write')")
    public ResponseEntity<ReviewResponse> updateReview(@Valid @RequestBody UpdateReviewRequest dto) {
        ReviewResponse review = reviewService.updateReview(dto);

        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('reviews:delete')")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);

        return new ResponseEntity<>("Review with ID = " + id + " has been deleted", HttpStatus.NO_CONTENT);
    }
}
