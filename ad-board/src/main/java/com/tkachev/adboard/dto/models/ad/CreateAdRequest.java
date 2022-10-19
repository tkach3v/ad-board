package com.tkachev.adboard.dto.models.ad;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tkachev.adboard.entity.AdStatus;
import com.tkachev.adboard.utils.DateFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

/**
 * A DTO for the {@link com.tkachev.adboard.entity.Ad} entity
 */
@Data
public class CreateAdRequest implements Serializable {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotNull
    private Double price;
    private AdStatus status;
    @NotNull
    private Long ownerId;
    @NotNull
    private Long categoryId;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateFormat.DD_MM_YYYY)
    private Date creationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateFormat.DD_MM_YYYY)
    private Date saleDate;
    private Boolean promotion;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateFormat.DD_MM_YYYY)
    private Date promotionEndDate;
}