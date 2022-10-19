package com.tkachev.adboard.dto.models.review;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link com.tkachev.adboard.entity.Review} entity
 */
@Data
public class CreateReviewRequest implements Serializable {
    @Min(1)
    @Max(5)
    @NotNull
    private Short score;
    @NotBlank
    private String comment;
    @NotNull
    private Long authorId;
    @NotNull
    private Long adId;
}