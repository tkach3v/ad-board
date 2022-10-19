package com.tkachev.adboard.dto.mappers;

import com.tkachev.adboard.dto.models.review.CreateReviewRequest;
import com.tkachev.adboard.dto.models.review.ReviewResponse;
import com.tkachev.adboard.dto.models.review.UpdateReviewRequest;
import com.tkachev.adboard.entity.Review;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring",
        uses = {AdMapper.class, UserMapper.class})
public interface ReviewMapper {
    @Mapping(source = "authorId", target = "author.id")
    @Mapping(source = "adId", target = "ad.id")
    Review toReview(CreateReviewRequest createReviewRequest);

    @Mapping(source = "authorId", target = "author.id")
    @Mapping(source = "adId", target = "ad.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Review updateReview(UpdateReviewRequest updateReviewRequest, @MappingTarget Review review);

    ReviewResponse toReviewResponse(Review review);
}
