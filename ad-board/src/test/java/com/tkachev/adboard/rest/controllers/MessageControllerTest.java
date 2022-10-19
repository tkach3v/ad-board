package com.tkachev.adboard.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tkachev.adboard.dto.models.message.CreateMessageRequest;
import com.tkachev.adboard.dto.models.message.MessageResponse;
import com.tkachev.adboard.dto.models.message.UpdateMessageRequest;
import com.tkachev.adboard.dto.models.user.CreateUserRequest;
import com.tkachev.adboard.dto.models.user.UpdateUserRequest;
import com.tkachev.adboard.dto.models.user.UserResponse;
import com.tkachev.adboard.security.JwtTokenProvider;
import com.tkachev.adboard.services.MessageService;
import com.tkachev.adboard.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.time.Year;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(MessageController.class)
class MessageControllerTest {

    @MockBean
    private MessageService messageService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String url = "/api/v1/messages";

    @Test
    void shouldCreateMessage() throws Exception {
        CreateMessageRequest createMessageRequest = new CreateMessageRequest();
        createMessageRequest.setContent("Some text!");
        createMessageRequest.setSenderId(1L);
        createMessageRequest.setChatId(1L);
        createMessageRequest.setDate(new Date(2022, 10, 17 ));

        when(messageService.createMessage(createMessageRequest)).thenReturn(new MessageResponse());

        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createMessageRequest)))
                .andExpect(status().isCreated())
                .andDo(print());

        verify(messageService).createMessage(createMessageRequest);
    }

    @Test
    void shouldUpdateMessage() throws Exception {
        UpdateMessageRequest updateMessageRequest = new UpdateMessageRequest();
        updateMessageRequest.setId(1L);
        updateMessageRequest.setContent("Some text!");
        updateMessageRequest.setSenderId(1L);
        updateMessageRequest.setChatId(1L);
        updateMessageRequest.setDate(new Date(2022, 10, 17 ));
        when(messageService.updateMessage(updateMessageRequest)).thenReturn(new MessageResponse());

        mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateMessageRequest)))
                .andExpect(status().isOk())
                .andDo(print());

        verify(messageService).updateMessage(any(UpdateMessageRequest.class));
    }

    @Test
    void shouldGetUMessages() throws Exception {
        when(messageService.getMessages()).thenReturn(new ArrayList<>());

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andDo(print());

        verify(messageService).getMessages();
    }

    @Test
    void shouldGetMessageById() throws Exception {
        Long messageId = 1L;
        when(messageService.getMessageById(messageId)).thenReturn(new MessageResponse());

        mockMvc.perform(get(url + "/" + messageId))
                .andExpect(status().isOk())
                .andDo(print());

        verify(messageService).getMessageById(messageId);
    }

    @Test
    void shouldDeleteMessage() throws Exception {
        Long messageId = 1L;

        mockMvc.perform(delete(url + "/" + messageId))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(messageService).deleteMessage(messageId);
    }
}