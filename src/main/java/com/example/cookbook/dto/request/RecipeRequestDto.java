package com.example.cookbook.dto.request;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class RecipeRequestDto {
    @NotNull
    private String name;
    private String description;
}
