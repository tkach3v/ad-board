package com.tkachev.adboard.dto.models.ad;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tkachev.adboard.dto.models.category.CategoryResponse;
import com.tkachev.adboard.dto.models.user.UserResponse;
import com.tkachev.adboard.entity.AdStatus;
import com.tkachev.adboard.utils.DateFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

/**
 * A DTO for the {@link com.tkachev.adboard.entity.Ad} entity
 */
@Data
public class AdResponse implements Serializable {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private AdStatus status;
    private UserResponse owner;
    private CategoryResponse category;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateFormat.DD_MM_YYYY)
    private Date creationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateFormat.DD_MM_YYYY)
    private Date saleDate;
    private Boolean promotion;
    private Date promotionEndDate;
}