package com.tkachev.adboard.repositories;

import com.tkachev.adboard.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByAuthorIdAndAdId(Long authorId, Long adId);
}
