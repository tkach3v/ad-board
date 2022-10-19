package com.tkachev.adboard.dto.models.category;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * A DTO for the {@link com.tkachev.adboard.entity.Category} entity
 */
@Data
public class CreateCategoryRequest implements Serializable {
    @NotBlank
    private String title;
}