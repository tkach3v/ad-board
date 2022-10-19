package com.tkachev.adboard.dto.mappers;

import com.tkachev.adboard.dto.models.user.CreateUserRequest;
import com.tkachev.adboard.dto.models.user.UpdateUserRequest;
import com.tkachev.adboard.dto.models.user.UserResponse;
import com.tkachev.adboard.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
    User toUser(CreateUserRequest createUserRequest);

    UserResponse toUserResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateUser(UpdateUserRequest updateUserRequest, @MappingTarget User user);
}
