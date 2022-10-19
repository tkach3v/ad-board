package com.tkachev.adboard.rest.controllers;

import com.tkachev.adboard.dto.models.chat.ChatResponse;
import com.tkachev.adboard.dto.models.chat.CreateChatRequest;
import com.tkachev.adboard.dto.models.chat.UpdateChatRequest;
import com.tkachev.adboard.services.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chats")
public class ChatController {

    private final ChatService chatService;

    @GetMapping
    @PreAuthorize("hasAuthority('chats:read')")
    public ResponseEntity<List<ChatResponse>> getChats() {
        List<ChatResponse> chats = chatService.getChats();

        return new ResponseEntity<>(chats, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('chats:write')")
    public ResponseEntity<ChatResponse> createChat(@Valid @RequestBody CreateChatRequest dto) {
        ChatResponse chat = chatService.createChat(dto);

        return new ResponseEntity<>(chat, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('chats:read')")
    public ResponseEntity<ChatResponse> getChatById(@PathVariable(name = "id") Long id) {
        ChatResponse chat = chatService.getChatById(id);

        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('chats:write')")
    public ResponseEntity<ChatResponse> updateChat(@Valid @RequestBody UpdateChatRequest dto) {
        ChatResponse chat = chatService.updateChat(dto);

        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('chats:delete')")
    public ResponseEntity<String> deleteChat(@PathVariable Long id) {
        chatService.deleteChat(id);

        return new ResponseEntity<>("Chat with ID = " + id + " has been deleted", HttpStatus.NO_CONTENT);
    }
}
