package com.tkachev.adboard.dto.models.category;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link com.tkachev.adboard.entity.Category} entity
 */
@Data
public class UpdateCategoryRequest implements Serializable {
    @NotNull
    private Long id;
    @NotBlank
    private String title;
}