package com.tkachev.adboard.services.impl;

import com.tkachev.adboard.dto.models.review.CreateReviewRequest;
import com.tkachev.adboard.dto.models.review.ReviewResponse;
import com.tkachev.adboard.dto.models.review.UpdateReviewRequest;
import com.tkachev.adboard.entity.Ad;
import com.tkachev.adboard.entity.Review;
import com.tkachev.adboard.entity.User;
import com.tkachev.adboard.exception_handling.exceptions.EntityAlreadyExistsException;
import com.tkachev.adboard.dto.mappers.ReviewMapper;
import com.tkachev.adboard.repositories.AdRepository;
import com.tkachev.adboard.repositories.ReviewRepository;
import com.tkachev.adboard.repositories.UserRepository;
import com.tkachev.adboard.services.AbstractService;
import com.tkachev.adboard.services.ReviewService;
import com.tkachev.adboard.utils.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl extends AbstractService implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final AdRepository adRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public ReviewResponse createReview(CreateReviewRequest dto) {
        Review review = reviewMapper.toReview(dto);
        linkAuthorAndAd(dto.getAuthorId(), dto.getAdId(), review);
        Optional<Review> reviewByAuthorAndAd = reviewRepository.findByAuthorIdAndAdId(dto.getAuthorId(), dto.getAdId());
        if (reviewByAuthorAndAd.isPresent()) {
            throw new EntityAlreadyExistsException("This ad already has a review from present user!");
        }
        reviewRepository.save(review);
        updateUserRating(review.getAd().getOwner());

        return reviewMapper.toReviewResponse(review);
    }

    @Override
    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id).orElse(null);
        review = isNotNull(review, "Review", id);
        reviewRepository.delete(review);
        updateUserRating(review.getAd().getOwner());
    }

    @Override
    public ReviewResponse updateReview(UpdateReviewRequest dto) {
        Review review = reviewRepository.findById(dto.getId()).orElse(null);
        review = isNotNull(review, "Review", dto.getId());
        reviewMapper.updateReview(dto, review);
        linkAuthorAndAd(dto.getAuthorId(), dto.getAdId(), review);
        Review reviewByAuthorAndAd = reviewRepository.
                findByAuthorIdAndAdId(dto.getAuthorId(), dto.getAdId()).orElse(null);
        if (reviewByAuthorAndAd != null && !reviewByAuthorAndAd.equals(review)) {
            throw new EntityAlreadyExistsException("This ad already has a review from present user!");
        }
        reviewRepository.save(review);
        updateUserRating(review.getAd().getOwner());

        return reviewMapper.toReviewResponse(review);
    }

    @Override
    public List<ReviewResponse> getReviews() {
        return reviewRepository.findAll().
                stream().
                map(reviewMapper::toReviewResponse).
                toList();
    }

    @Override
    public ReviewResponse getReviewById(Long id) {
        Review review = reviewRepository.findById(id).orElse(null);
        review = isNotNull(review, "User", id);

        return reviewMapper.toReviewResponse(review);
    }

    private void linkAuthorAndAd(Long authorId, Long adId, Review review) {
        User author = userRepository.findById(authorId).orElse(null);
        author = isNotNull(author, "User", authorId);
        Ad ad = adRepository.findById(adId).orElse(null);
        ad = isNotNull(ad, "Ad", adId);
        review.setAd(ad);
        review.setAuthor(author);
    }

    private void updateUserRating(User user) {
        float scoreSum = 0f;
        int scoreAmount = 0;

        for (Ad ad : user.getAds()) {
            Set<Review> reviews = ad.getReviews();
            scoreSum += reviews.stream().mapToDouble(Review::getScore).sum();
            scoreAmount += reviews.size();
        }

        if (scoreAmount != 0) {
            user.setRating(Util.round(scoreSum / scoreAmount, 1));
        } else {
            user.setRating(0f);
        }

        userRepository.save(user);
    }
}
