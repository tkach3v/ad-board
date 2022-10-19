package com.tkachev.adboard.dto.models.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tkachev.adboard.entity.MessageStatus;
import com.tkachev.adboard.utils.DateFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

/**
 * A DTO for the {@link com.tkachev.adboard.entity.Message} entity
 */
@Data
public class UpdateMessageRequest implements Serializable {
    @NotNull
    private Long id;
    @NotBlank
    private String content;
    private MessageStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateFormat.DD_MM_YYYY)
    private Date date;
    @NotNull
    private Long chatId;
    @NotNull
    private Long senderId;
}