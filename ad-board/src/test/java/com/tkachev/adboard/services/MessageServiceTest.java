package com.tkachev.adboard.services;

import com.tkachev.adboard.config.TestConfig;
import com.tkachev.adboard.dto.models.chat.CreateChatRequest;
import com.tkachev.adboard.dto.models.message.CreateMessageRequest;
import com.tkachev.adboard.dto.models.message.MessageResponse;
import com.tkachev.adboard.dto.models.message.UpdateMessageRequest;
import com.tkachev.adboard.dto.models.user.CreateUserRequest;
import com.tkachev.adboard.dto.models.user.UpdateUserRequest;
import com.tkachev.adboard.dto.models.user.UserResponse;
import com.tkachev.adboard.entity.*;
import com.tkachev.adboard.exception_handling.exceptions.NoSuchEntityException;
import com.tkachev.adboard.exception_handling.exceptions.NoSuchUserInChatException;
import com.tkachev.adboard.repositories.ChatRepository;
import com.tkachev.adboard.repositories.MessageRepository;
import com.tkachev.adboard.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest
class MessageServiceTest {

    @Autowired
    private MessageService messageService;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void beforeEach() {
        reset(messageRepository);
    }

    @Test
    void checkChatAndUserExistenceAndCallSaveMessageWhenCreatingMessage() {
        CreateMessageRequest dto = new CreateMessageRequest();
        dto.setChatId(1L);
        dto.setSenderId(1L);
        Chat chat = new Chat();
        User user1 = new User();
        User user2 = new User();
        user1.setId(1L);
        user2.setId(2L);
        chat.setUsers(Set.of(user1, user2));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(chatRepository.findById(1L)).thenReturn(Optional.of(chat));
        messageService.createMessage(dto);

        verify(chatRepository).findById(dto.getChatId());
        verify(userRepository).findById(dto.getSenderId());
        verify(messageRepository).save(any(Message.class));
    }

    @Test
    void throwExceptionIfUserIsNotMemberOfChatWhenCreatingMessage() {
        CreateMessageRequest dto = new CreateMessageRequest();
        dto.setChatId(1L);
        dto.setSenderId(3L);
        Chat chat = new Chat();
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        user1.setId(1L);
        user2.setId(2L);
        chat.setUsers(Set.of(user1, user2));
        when(userRepository.findById(3L)).thenReturn(Optional.of(user3));
        when(chatRepository.findById(1L)).thenReturn(Optional.of(chat));

        assertThrows(NoSuchUserInChatException.class, () -> messageService.createMessage(dto));
    }

    @Test
    void throwExceptionIfUserDoesNotExistWhenCreatingMessage() {
        CreateMessageRequest dto = new CreateMessageRequest();
        dto.setChatId(1L);
        dto.setSenderId(3L);
        User user3 = new User();

        when(userRepository.findById(3L)).thenReturn(Optional.of(user3));
        when(chatRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchEntityException.class, () -> messageService.createMessage(dto));
    }

    @Test
    void throwExceptionIfChatDoesNotExistWhenCreatingMessage() {
        CreateMessageRequest dto = new CreateMessageRequest();
        dto.setChatId(1L);
        dto.setSenderId(3L);
        Chat chat1 = new Chat();

        when(userRepository.findById(3L)).thenReturn(Optional.empty());
        when(chatRepository.findById(1L)).thenReturn(Optional.of(chat1));

        assertThrows(NoSuchEntityException.class, () -> messageService.createMessage(dto));
    }

    @Test
    void setDefaultParametersIfTheyMissedWhenCreatingMessage() {
        CreateMessageRequest dto = new CreateMessageRequest();
        dto.setChatId(1L);
        dto.setSenderId(1L);
        Chat chat = new Chat();
        User user1 = new User();
        User user2 = new User();
        user1.setId(1L);
        user2.setId(2L);
        chat.setUsers(Set.of(user1, user2));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(chatRepository.findById(1L)).thenReturn(Optional.of(chat));

        MessageResponse messageResponse = messageService.createMessage(dto);

        assertEquals(MessageStatus.SENT, messageResponse.getStatus());
    }

    @Test
    void checkMessageExistenceAndCallDeleteMessageWhenDeletingMessage() {
        when(messageRepository.findById(anyLong())).thenReturn(Optional.of(new Message()));

        messageService.deleteMessage(anyLong());

        verify(messageRepository).findById(anyLong());
        verify(messageRepository).delete(any(Message.class));
    }

    @Test
    void throwExceptionIfMessageDoesNotExistWhenDeletingMessage() {
        when(messageRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchEntityException.class, () -> messageService.deleteMessage(anyLong()));
    }

    @Test
    void checkUserAndChatAndMessageExistenceAndSaveMessageWhenUpdatingMessage() {
        UpdateMessageRequest dto = new UpdateMessageRequest();
        dto.setId(1L);
        dto.setChatId(1L);
        dto.setSenderId(1L);
        Chat chat = new Chat();
        User user1 = new User();
        User user2 = new User();
        user1.setId(1L);
        user2.setId(2L);
        chat.setUsers(Set.of(user1, user2));
        when(messageRepository.findById(1L)).thenReturn(Optional.of(new Message()));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(chatRepository.findById(1L)).thenReturn(Optional.of(chat));

        messageService.updateMessage(dto);

        verify(messageRepository).findById(dto.getId());
        verify(chatRepository).findById(dto.getChatId());
        verify(userRepository).findById(dto.getSenderId());
        verify(messageRepository).save(any(Message.class));
    }

    @Test
    void throwExceptionIfMessageDoesNotExistWhenUpdatingMessage() {
        UpdateMessageRequest dto = new UpdateMessageRequest();
        dto.setId(1L);
        when(messageRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchEntityException.class, () -> messageService.updateMessage(dto));
    }

    @Test
    void throwExceptionIfUserIsNotMemberOfChatWhenUpdatingMessage() {
        UpdateMessageRequest dto = new UpdateMessageRequest();
        dto.setId(1L);
        dto.setChatId(1L);
        dto.setSenderId(3L);
        Chat chat = new Chat();
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        user1.setId(1L);
        user2.setId(2L);
        chat.setUsers(Set.of(user1, user2));
        when(messageRepository.findById(1L)).thenReturn(Optional.of(new Message()));
        when(userRepository.findById(3L)).thenReturn(Optional.of(user3));
        when(chatRepository.findById(1L)).thenReturn(Optional.of(chat));

        assertThrows(NoSuchUserInChatException.class, () -> messageService.updateMessage(dto));
    }

    @Test
    void throwExceptionIfUserDoesNotExistWhenUpdatingMessage() {
        UpdateMessageRequest dto = new UpdateMessageRequest();
        dto.setId(1L);
        dto.setChatId(1L);
        dto.setSenderId(3L);
        User user3 = new User();
        when(messageRepository.findById(1L)).thenReturn(Optional.of(new Message()));
        when(userRepository.findById(3L)).thenReturn(Optional.of(user3));
        when(chatRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchEntityException.class, () -> messageService.updateMessage(dto));
    }

    @Test
    void throwExceptionIfChatDoesNotExistWhenUpdatingMessage() {
        UpdateMessageRequest dto = new UpdateMessageRequest();
        dto.setId(1L);
        dto.setChatId(1L);
        dto.setSenderId(3L);
        Chat chat1 = new Chat();
        when(messageRepository.findById(1L)).thenReturn(Optional.of(new Message()));
        when(userRepository.findById(3L)).thenReturn(Optional.empty());
        when(chatRepository.findById(1L)).thenReturn(Optional.of(chat1));

        assertThrows(NoSuchEntityException.class, () -> messageService.updateMessage(dto));
    }

    @Test
    void callFindAllWhenGettingMessages() {
        messageService.getMessages();

        verify(messageRepository).findAll();
    }

    @Test
    void checkMessageExistenceWhenGettingMessageById() {
        when(messageRepository.findById(anyLong())).thenReturn(Optional.of(new Message()));

        messageService.getMessageById(anyLong());

        verify(messageRepository).findById(anyLong());
    }

    @Test
    void throwExceptionIfMessageDoesNotExistWhenGettingMessageById() {
        when(messageRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchEntityException.class, () -> messageService.getMessageById(anyLong()));
    }
}