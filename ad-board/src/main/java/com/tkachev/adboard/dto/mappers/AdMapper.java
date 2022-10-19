package com.tkachev.adboard.dto.mappers;

import com.tkachev.adboard.dto.models.ad.AdResponse;
import com.tkachev.adboard.dto.models.ad.CreateAdRequest;
import com.tkachev.adboard.dto.models.ad.UpdateAdRequest;
import com.tkachev.adboard.entity.Ad;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring",
        uses = UserMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AdMapper {
    @Mapping(source = "ownerId", target = "owner.id")
    @Mapping(source = "categoryId", target = "category.id")
    Ad toAd(CreateAdRequest createAdRequest);

    AdResponse toAdResponse(Ad ad);

    @Mapping(source = "ownerId", target = "owner.id")
    @Mapping(source = "categoryId", target = "category.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Ad updateAd(UpdateAdRequest updateAdRequest, @MappingTarget Ad ad);
}
