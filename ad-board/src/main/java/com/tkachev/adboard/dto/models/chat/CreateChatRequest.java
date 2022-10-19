package com.tkachev.adboard.dto.models.chat;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link com.tkachev.adboard.entity.Chat} entity
 */
@Data
public class CreateChatRequest implements Serializable {
    @NotEmpty
    @Size(min = 2, max = 2, message = "Number of chat members from 2 to 2")
    private Set<Long> userIds;
}