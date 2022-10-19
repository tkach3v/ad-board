package com.tkachev.adboard.dto.models.review;

import com.tkachev.adboard.dto.models.ad.AdResponse;
import com.tkachev.adboard.dto.models.user.UserResponse;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.tkachev.adboard.entity.Review} entity
 */
@Data
public class ReviewResponse implements Serializable {
    private Long id;
    private Short score;
    private String comment;
    private UserResponse author;
    private AdResponse ad;
}