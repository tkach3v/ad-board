package com.tkachev.adboard.dto.mappers;

import com.tkachev.adboard.dto.models.category.CategoryResponse;
import com.tkachev.adboard.dto.models.category.CreateCategoryRequest;
import com.tkachev.adboard.dto.models.category.UpdateCategoryRequest;
import com.tkachev.adboard.entity.Category;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CreateCategoryRequest createCategoryRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Category updateCategory(UpdateCategoryRequest updateCategoryRequest, @MappingTarget Category category);

    CategoryResponse toCategoryResponse(Category category);
}
