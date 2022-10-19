package com.tkachev.adboard.dto.mappers;

import com.tkachev.adboard.dto.models.chat.ChatResponse;
import com.tkachev.adboard.dto.models.chat.CreateChatRequest;
import com.tkachev.adboard.dto.models.chat.UpdateChatRequest;
import com.tkachev.adboard.entity.Chat;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring",
        uses = {MessageMapper.class, UserMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ChatMapper {
    Chat toChat(CreateChatRequest createChatRequest);

    ChatResponse toChatResponse(Chat chat);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Chat updateChat(UpdateChatRequest updateChatRequest, @MappingTarget Chat chat);
}
