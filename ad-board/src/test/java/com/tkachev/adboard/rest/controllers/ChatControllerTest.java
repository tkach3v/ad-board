package com.tkachev.adboard.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tkachev.adboard.dto.models.chat.ChatResponse;
import com.tkachev.adboard.dto.models.chat.CreateChatRequest;
import com.tkachev.adboard.dto.models.chat.UpdateChatRequest;
import com.tkachev.adboard.dto.models.user.CreateUserRequest;
import com.tkachev.adboard.dto.models.user.UpdateUserRequest;
import com.tkachev.adboard.dto.models.user.UserResponse;
import com.tkachev.adboard.security.JwtTokenProvider;
import com.tkachev.adboard.services.ChatService;
import com.tkachev.adboard.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ChatController.class)
class ChatControllerTest {

    @MockBean
    private ChatService chatService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String url = "/api/v1/chats";

    @Test
    void shouldCreateChat() throws Exception {
        CreateChatRequest createChatRequest = new CreateChatRequest();
        Long userId1 = 1L;
        Long userId2 = 2L;
        Set<Long> users = Set.of(userId1, userId2);
        createChatRequest.setUserIds(users);

        when(chatService.createChat(createChatRequest)).thenReturn(new ChatResponse());

        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createChatRequest)))
                .andExpect(status().isCreated())
                .andDo(print());

        verify(chatService).createChat(createChatRequest);
    }

    @Test
    void shouldUpdateChat() throws Exception {
        UpdateChatRequest updateChatRequest = new UpdateChatRequest();
        updateChatRequest.setId(1L);
        when(chatService.updateChat(updateChatRequest)).thenReturn(new ChatResponse());

        mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateChatRequest)))
                .andExpect(status().isOk())
                .andDo(print());

        verify(chatService).updateChat(updateChatRequest);
    }

    @Test
    void shouldGetChats() throws Exception {
        when(chatService.getChats()).thenReturn(new ArrayList<>());

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andDo(print());

        verify(chatService).getChats();
    }

    @Test
    void shouldGetChatById() throws Exception {
        Long chatId = 1L;
        when(chatService.getChatById(chatId)).thenReturn(new ChatResponse());

        mockMvc.perform(get(url + "/" + chatId))
                .andExpect(status().isOk())
                .andDo(print());

        verify(chatService).getChatById(chatId);
    }

    @Test
    void shouldDeleteChat() throws Exception {
        Long chatId = 1L;

        mockMvc.perform(delete(url + "/" + chatId))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(chatService).deleteChat(chatId);
    }
}