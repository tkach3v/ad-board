package com.tkachev.adboard.dto.mappers;

import com.tkachev.adboard.dto.models.message.CreateMessageRequest;
import com.tkachev.adboard.dto.models.message.MessageResponse;
import com.tkachev.adboard.dto.models.message.UpdateMessageRequest;
import com.tkachev.adboard.entity.Message;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface MessageMapper {

    @Mapping(source = "chat.id", target = "chatId")
    @Mapping(source = "sender.id", target = "senderId")
    MessageResponse toMessageResponse(Message message);

    @Mapping(source = "chatId", target = "chat.id")
    @Mapping(source = "senderId", target = "sender.id")
    Message toMessage(CreateMessageRequest createMessageRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Message updateMessage(UpdateMessageRequest updateMessageRequest, @MappingTarget Message message);
}
