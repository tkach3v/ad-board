package com.tkachev.adboard.services;

import com.tkachev.adboard.dto.models.chat.ChatResponse;
import com.tkachev.adboard.dto.models.chat.CreateChatRequest;
import com.tkachev.adboard.dto.models.chat.UpdateChatRequest;

import java.util.List;

public interface ChatService {

    ChatResponse createChat(CreateChatRequest dto);

    void deleteChat(Long id);

    ChatResponse updateChat(UpdateChatRequest dto);

    List<ChatResponse> getChats();

    ChatResponse getChatById(Long id);
}
