package com.tkachev.adboard.dto.models.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tkachev.adboard.entity.MessageStatus;
import com.tkachev.adboard.utils.DateFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

/**
 * A DTO for the {@link com.tkachev.adboard.entity.Message} entity
 */
@Data
public class MessageResponse implements Serializable {
    private Long id;
    private String content;
    private MessageStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateFormat.DD_MM_YYYY_HH_MM_SS)
    private Date date;
    private Long chatId;
    private Long senderId;
}