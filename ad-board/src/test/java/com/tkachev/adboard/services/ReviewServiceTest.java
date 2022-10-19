package com.tkachev.adboard.services;

import com.tkachev.adboard.config.TestConfig;
import com.tkachev.adboard.dto.models.message.CreateMessageRequest;
import com.tkachev.adboard.dto.models.message.MessageResponse;
import com.tkachev.adboard.dto.models.message.UpdateMessageRequest;
import com.tkachev.adboard.dto.models.review.CreateReviewRequest;
import com.tkachev.adboard.dto.models.review.UpdateReviewRequest;
import com.tkachev.adboard.entity.*;
import com.tkachev.adboard.exception_handling.exceptions.EntityAlreadyExistsException;
import com.tkachev.adboard.exception_handling.exceptions.NoSuchEntityException;
import com.tkachev.adboard.exception_handling.exceptions.NoSuchUserInChatException;
import com.tkachev.adboard.repositories.AdRepository;
import com.tkachev.adboard.repositories.ReviewRepository;
import com.tkachev.adboard.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest
class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdRepository adRepository;

    @BeforeEach
    private void beforeEach() {
        reset(reviewRepository, userRepository, adRepository);
    }

    @Test
    void checkUserAndAdExistenceAndCallSaveReviewAndUpdateUserRatingWhenCreatingReview() {
        CreateReviewRequest dto = new CreateReviewRequest();
        dto.setAdId(1L);
        dto.setAuthorId(1L);
        Ad ad = new Ad();
        User user = new User();
        user.setAds(new HashSet<>());
        ad.setOwner(user);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(adRepository.findById(dto.getAdId())).thenReturn(Optional.of(ad));
        reviewService.createReview(dto);

        verify(adRepository).findById(dto.getAdId());
        verify(userRepository).findById(dto.getAuthorId());
        verify(reviewRepository).save(any(Review.class));
        verify(userRepository).save(any(User.class));
    }

    @Test
    void throwExceptionIfReviewAlreadyExistsFromCurrentUserWhenCreatingReview() {
        CreateReviewRequest dto = new CreateReviewRequest();
        dto.setAdId(1L);
        dto.setAuthorId(1L);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(adRepository.findById(dto.getAdId())).thenReturn(Optional.of(new Ad()));
        when(reviewRepository.findByAuthorIdAndAdId(dto.getAuthorId(), dto.getAdId())).thenReturn(Optional.of(new Review()));

        assertThrows(EntityAlreadyExistsException.class, () -> reviewService.createReview(dto));
        verify(reviewRepository).findByAuthorIdAndAdId(dto.getAuthorId(), dto.getAdId());
    }

    @Test
    void throwExceptionIfAuthorDoesNotExistWhenCreatingReview() {
        CreateReviewRequest dto = new CreateReviewRequest();
        dto.setAdId(1L);
        dto.setAuthorId(1L);

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(adRepository.findById(dto.getAdId())).thenReturn(Optional.of(new Ad()));

        assertThrows(NoSuchEntityException.class, () -> reviewService.createReview(dto));
    }

    @Test
    void throwExceptionIfAdDoesNotExistWhenCreatingReview() {
        CreateReviewRequest dto = new CreateReviewRequest();
        dto.setAdId(1L);
        dto.setAuthorId(1L);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(adRepository.findById(dto.getAdId())).thenReturn(Optional.empty());

        assertThrows(NoSuchEntityException.class, () -> reviewService.createReview(dto));
    }

    @Test
    void checkMessageExistenceAndCallDeleteMessageWhenDeletingReview() {
        Ad ad = new Ad();
        User user = new User();
        Review review = new Review();
        user.setAds(new HashSet<>());
        ad.setOwner(user);
        review.setAd(ad);

        when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(review));

        reviewService.deleteReview(anyLong());

        verify(reviewRepository).findById(anyLong());
        verify(reviewRepository).delete(any(Review.class));
        verify(userRepository).save(user);
    }

    @Test
    void throwExceptionIfReviewDoesNotExistWhenDeletingReview() {
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchEntityException.class, () -> reviewService.deleteReview(anyLong()));
    }

    @Test
    void checkUserAndAdExistenceAndCallSaveReviewAndUpdateUserRatingWhenUpdatingReview() {
        UpdateReviewRequest dto = new UpdateReviewRequest();
        dto.setId(1L);
        dto.setAdId(1L);
        dto.setAuthorId(1L);
        Ad ad = new Ad();
        User user = new User();
        user.setAds(new HashSet<>());
        ad.setOwner(user);

        when(reviewRepository.findById(dto.getId())).thenReturn(Optional.of(new Review()));
        when(userRepository.findById(dto.getAuthorId())).thenReturn(Optional.of(new User()));
        when(adRepository.findById(dto.getAdId())).thenReturn(Optional.of(ad));
        reviewService.updateReview(dto);

        verify(reviewRepository).findById(dto.getId());
        verify(adRepository).findById(dto.getAdId());
        verify(userRepository).findById(dto.getAuthorId());
        verify(reviewRepository).save(any(Review.class));
        verify(userRepository).save(any(User.class));
    }

    @Test
    void throwExceptionIfReviewAlreadyExistsFromCurrentUserWhenUpdatingReview() {
        UpdateReviewRequest dto = new UpdateReviewRequest();
        dto.setId(1L);
        dto.setAdId(1L);
        dto.setAuthorId(1L);

        when(reviewRepository.findById(dto.getId())).thenReturn(Optional.of(new Review()));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(adRepository.findById(dto.getAdId())).thenReturn(Optional.of(new Ad()));
        when(reviewRepository.findByAuthorIdAndAdId(dto.getAuthorId(), dto.getAdId())).thenReturn(Optional.of(new Review()));

        assertThrows(EntityAlreadyExistsException.class, () -> reviewService.updateReview(dto));
        verify(reviewRepository).findByAuthorIdAndAdId(dto.getAuthorId(), dto.getAdId());
    }

    @Test
    void throwExceptionIfReviewDoesNotExistWhenUpdatingReview() {
        UpdateReviewRequest dto = new UpdateReviewRequest();
        dto.setId(1L);
        dto.setAdId(1L);
        dto.setAuthorId(1L);

        when(reviewRepository.findById(dto.getId())).thenReturn(Optional.empty());
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(adRepository.findById(dto.getAdId())).thenReturn(Optional.of(new Ad()));

        assertThrows(NoSuchEntityException.class, () -> reviewService.updateReview(dto));
    }

    @Test
    void throwExceptionIfAuthorDoesNotExistWhenUpdatingReview() {
        UpdateReviewRequest dto = new UpdateReviewRequest();
        dto.setId(1L);
        dto.setAdId(1L);
        dto.setAuthorId(1L);

        when(reviewRepository.findById(dto.getId())).thenReturn(Optional.of(new Review()));
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(adRepository.findById(dto.getAdId())).thenReturn(Optional.of(new Ad()));

        assertThrows(NoSuchEntityException.class, () -> reviewService.updateReview(dto));
    }

    @Test
    void throwExceptionIfAdDoesNotExistWhenUpdatingReview() {
        UpdateReviewRequest dto = new UpdateReviewRequest();
        dto.setId(1L);
        dto.setAdId(1L);
        dto.setAuthorId(1L);

        when(reviewRepository.findById(dto.getId())).thenReturn(Optional.of(new Review()));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(adRepository.findById(dto.getAdId())).thenReturn(Optional.empty());

        assertThrows(NoSuchEntityException.class, () -> reviewService.updateReview(dto));
    }

    @Test
    void callFindAllWhenGettingReviews() {
        reviewService.getReviews();

        verify(reviewRepository).findAll();
    }

    @Test
    void checkReviewExistenceWhenGettingReviewById() {
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(new Review()));

        reviewService.getReviewById(anyLong());

        verify(reviewRepository).findById(anyLong());
    }

    @Test
    void throwExceptionIfReviewDoesNotExistWhenGettingReviewById() {
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchEntityException.class, () -> reviewService.getReviewById(anyLong()));
    }
}