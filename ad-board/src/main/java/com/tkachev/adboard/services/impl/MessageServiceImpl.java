package com.tkachev.adboard.services.impl;

import com.tkachev.adboard.dto.models.message.CreateMessageRequest;
import com.tkachev.adboard.dto.models.message.MessageResponse;
import com.tkachev.adboard.dto.models.message.UpdateMessageRequest;
import com.tkachev.adboard.entity.Chat;
import com.tkachev.adboard.entity.Message;
import com.tkachev.adboard.entity.MessageStatus;
import com.tkachev.adboard.entity.User;
import com.tkachev.adboard.exception_handling.exceptions.NoSuchUserInChatException;
import com.tkachev.adboard.dto.mappers.MessageMapper;
import com.tkachev.adboard.repositories.ChatRepository;
import com.tkachev.adboard.repositories.MessageRepository;
import com.tkachev.adboard.repositories.UserRepository;
import com.tkachev.adboard.services.AbstractService;
import com.tkachev.adboard.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl extends AbstractService implements MessageService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final MessageMapper messageMapper;

    @Override
    public MessageResponse createMessage(CreateMessageRequest dto) {
        Message message = messageMapper.toMessage(dto);
        linkUserAndChat(dto.getSenderId(), dto.getChatId(), message);
        message.setStatus(Objects.requireNonNullElse(message.getStatus(), MessageStatus.SENT));
        messageRepository.save(message);

        return messageMapper.toMessageResponse(message);
    }

    @Override
    public void deleteMessage(Long id) {
        Message message = messageRepository.findById(id).orElse(null);
        message = isNotNull(message, "Message", id);
        messageRepository.delete(message);
    }

    @Override
    public MessageResponse updateMessage(UpdateMessageRequest dto) {
        Message message = messageRepository.findById(dto.getId()).orElse(null);
        message = isNotNull(message, "Message", dto.getId());
        messageMapper.updateMessage(dto, message);
        linkUserAndChat(dto.getSenderId(), dto.getChatId(), message);
        messageRepository.save(message);

        return messageMapper.toMessageResponse(message);
    }

    @Override
    public List<MessageResponse> getMessages() {
        return messageRepository.findAll().
                stream().
                map(messageMapper::toMessageResponse).
                toList();
    }

    @Override
    public MessageResponse getMessageById(Long id) {
        Message message = messageRepository.findById(id).orElse(null);
        message = isNotNull(message, "Message", id);

        return messageMapper.toMessageResponse(message);
    }

    private void linkUserAndChat(Long senderId, Long chatId, Message message) {
        Chat chat = isNotNull(
                chatRepository.findById(chatId).orElse(null), "Chat", chatId);
        message.setChat(chat);

        User user = isNotNull(
                userRepository.findById(senderId).orElse(null), "User", senderId);
        if (!chat.getUsers().contains(user)) {
            throw new NoSuchUserInChatException("Sender is not a member of this chat!");
        }
        message.setSender(user);
    }
}
