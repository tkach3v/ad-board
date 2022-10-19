package com.tkachev.adboard.dto.models.chat;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link com.tkachev.adboard.entity.Chat} entity
 */
@Data
public class UpdateChatRequest implements Serializable {
    @NotNull
    private Long id;
}