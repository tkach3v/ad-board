package com.tkachev.adboard.services;

import com.tkachev.adboard.config.TestConfig;
import com.tkachev.adboard.dto.models.chat.ChatResponse;
import com.tkachev.adboard.dto.models.chat.CreateChatRequest;
import com.tkachev.adboard.dto.models.chat.UpdateChatRequest;
import com.tkachev.adboard.dto.models.user.UpdateUserRequest;
import com.tkachev.adboard.entity.Chat;
import com.tkachev.adboard.entity.User;
import com.tkachev.adboard.exception_handling.exceptions.EntityAlreadyExistsException;
import com.tkachev.adboard.exception_handling.exceptions.NoSuchEntityException;
import com.tkachev.adboard.repositories.ChatRepository;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest
class ChatServiceTest {

    @Autowired
    private ChatService chatService;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    private void beforeEach() {
        reset(chatRepository, userRepository);
    }

    @Test
    void checkUserExistenceAndSaveChatWhenCreatingChat() {
        CreateChatRequest dto = new CreateChatRequest();
        dto.setUserIds(Set.of(1L, 2L));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));

        ChatResponse chatResponse = chatService.createChat(dto);

        verify(userRepository, times(2)).findById(anyLong());
        verify(chatRepository).save(any(Chat.class));
    }

    @Test
    void throwExceptionIfUserDoesNotExistWhenCreatingChat() {
        CreateChatRequest dto = new CreateChatRequest();
        dto.setUserIds(Set.of(1L, 2L));
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchEntityException.class, () -> chatService.createChat(dto));
    }

    @Test
    void throwExceptionIfChatAlreadyExistsWhenCreatingChat() {
        CreateChatRequest dto = new CreateChatRequest();
        dto.setUserIds(Set.of(1L, 2L));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(chatRepository.findByUsersIn(any(Set.class))).thenReturn(Optional.of(new Chat()));

        assertThrows(EntityAlreadyExistsException.class, () -> chatService.createChat(dto));
    }

    @Test
    void checkChatExistenceAndCallDeleteChatWhenDeletingChat() {
        when(chatRepository.findById(anyLong())).thenReturn(Optional.of(new Chat()));

        chatService.deleteChat(anyLong());

        verify(chatRepository).findById(anyLong());
        verify(chatRepository).delete(any(Chat.class));
    }

    @Test
    void throwExceptionIfUserDoesNotExistWhenDeletingChat() {
        when(chatRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchEntityException.class, () -> chatService.deleteChat(anyLong()));
    }

    @Test
    void checkChatExistenceAndSaveChatWhenUpdatingChat() {
        UpdateChatRequest dto = new UpdateChatRequest();
        dto.setId(0L);
        when(chatRepository.findById(anyLong())).thenReturn(Optional.of(new Chat()));

        chatService.updateChat(dto);

        verify(chatRepository).findById(anyLong());
        verify(chatRepository).save(any(Chat.class));
    }

    @Test
    void throwExceptionIfUserDoesNotExistWhenUpdatingUser() {
        UpdateChatRequest dto = new UpdateChatRequest();
        dto.setId(0L);
        when(chatRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchEntityException.class, () -> chatService.updateChat(dto));
    }

    @Test
    void callFindAllWhenGettingChats() {
        chatService.getChats();

        verify(chatRepository).findAll();
    }

    @Test
    void checkChatExistenceWhenGettingChatById() {
        when(chatRepository.findById(anyLong())).thenReturn(Optional.of(new Chat()));

        chatService.getChatById(anyLong());

        verify(chatRepository).findById(anyLong());
    }

    @Test
    void throwExceptionIfChatDoesNotExistWhenGettingChatById() {
        when(chatRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchEntityException.class, () -> chatService.getChatById(anyLong()));
    }
}