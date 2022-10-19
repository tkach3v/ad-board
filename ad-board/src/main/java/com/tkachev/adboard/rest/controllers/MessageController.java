package com.tkachev.adboard.rest.controllers;

import com.tkachev.adboard.dto.models.message.CreateMessageRequest;
import com.tkachev.adboard.dto.models.message.MessageResponse;
import com.tkachev.adboard.dto.models.message.UpdateMessageRequest;
import com.tkachev.adboard.services.MessageService;
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
@RequestMapping("/api/v1/messages")
public class MessageController {

    private final MessageService messageService;

    @GetMapping
    @PreAuthorize("hasAuthority('messages:read')")
    public ResponseEntity<List<MessageResponse>> getMessages() {
        List<MessageResponse> messages = messageService.getMessages();

        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('messages:write')")
    public ResponseEntity<MessageResponse> createMessage(@Valid @RequestBody CreateMessageRequest dto) {
        MessageResponse message = messageService.createMessage(dto);

        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('messages:read')")
    public ResponseEntity<MessageResponse> getMessageById(@PathVariable(name = "id") Long id) {
        MessageResponse message = messageService.getMessageById(id);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('messages:write')")
    public ResponseEntity<MessageResponse> updateMessage(@Valid @RequestBody UpdateMessageRequest dto) {
        MessageResponse message = messageService.updateMessage(dto);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('messages:delete')")
    public ResponseEntity<String> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);

        return new ResponseEntity<>("Message with ID = " + id + " has been deleted", HttpStatus.NO_CONTENT);
    }
}
