package com.tkachev.adboard.services;

import com.tkachev.adboard.dto.models.message.CreateMessageRequest;
import com.tkachev.adboard.dto.models.message.MessageResponse;
import com.tkachev.adboard.dto.models.message.UpdateMessageRequest;

import java.util.List;

public interface MessageService {

    MessageResponse createMessage(CreateMessageRequest dto);

    void deleteMessage(Long id);

    MessageResponse updateMessage(UpdateMessageRequest dto);

    List<MessageResponse> getMessages();

    MessageResponse getMessageById(Long id);
}
