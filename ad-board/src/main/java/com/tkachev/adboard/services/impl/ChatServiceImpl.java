package com.tkachev.adboard.services.impl;

import com.tkachev.adboard.dto.models.chat.ChatResponse;
import com.tkachev.adboard.dto.models.chat.CreateChatRequest;
import com.tkachev.adboard.dto.models.chat.UpdateChatRequest;
import com.tkachev.adboard.entity.Chat;
import com.tkachev.adboard.entity.User;
import com.tkachev.adboard.exception_handling.exceptions.EntityAlreadyExistsException;
import com.tkachev.adboard.dto.mappers.ChatMapper;
import com.tkachev.adboard.repositories.ChatRepository;
import com.tkachev.adboard.repositories.UserRepository;
import com.tkachev.adboard.services.AbstractService;
import com.tkachev.adboard.services.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl extends AbstractService implements ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatMapper chatMapper;

    @Override
    public ChatResponse createChat(CreateChatRequest dto) {
        Chat chat = chatMapper.toChat(dto);
        Set<User> users = new HashSet<>();
        dto.getUserIds().
                forEach(i -> users.add(isNotNull(userRepository.findById(i).orElse(null), "User", i)));
        Optional<Chat> chatByUsers = chatRepository.findByUsersIn(users);
        if (chatByUsers.isPresent()) {
            throw new EntityAlreadyExistsException("This Chat already exists!");
        }
        chat.setUsers(users);
        users.forEach(i -> i.addChatToUser(chat));
        chatRepository.save(chat);

        return chatMapper.toChatResponse(chat);
    }

    @Override
    public void deleteChat(Long id) {
        Chat chat = chatRepository.findById(id).orElse(null);
        chat = isNotNull(chat, "Chat", id);
        chatRepository.delete(chat);
    }

    @Override
    public ChatResponse updateChat(UpdateChatRequest dto) {
        Chat chat = chatRepository.findById(dto.getId()).orElse(null);
        chat = isNotNull(chat, "Chat", dto.getId());
        chatMapper.updateChat(dto, chat);
        chatRepository.save(chat);

        return chatMapper.toChatResponse(chat);
    }

    @Override
    public List<ChatResponse> getChats() {
        return chatRepository.findAll().
                stream().
                map(chatMapper::toChatResponse).
                toList();
    }

    @Override
    public ChatResponse getChatById(Long id) {
        Chat chat = chatRepository.findById(id).orElse(null);
        chat = isNotNull(chat, "Chat", id);

        return chatMapper.toChatResponse(chat);
    }
}
