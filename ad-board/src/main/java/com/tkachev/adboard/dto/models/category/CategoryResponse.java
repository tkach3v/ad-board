package com.tkachev.adboard.dto.models.category;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.tkachev.adboard.entity.Category} entity
 */
@Data
public class CategoryResponse implements Serializable {
    private Long id;
    private String title;
}