package com.tkachev.adboard.dto.models.chat;

import com.tkachev.adboard.dto.models.message.MessageResponse;
import com.tkachev.adboard.dto.models.user.UserResponse;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link com.tkachev.adboard.entity.Chat} entity
 */
@Data
public class ChatResponse implements Serializable {
    private Long id;
    private Set<UserResponse> users;
    private Set<MessageResponse> messages;
}