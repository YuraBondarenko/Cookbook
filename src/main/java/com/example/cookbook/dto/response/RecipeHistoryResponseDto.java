package com.example.cookbook.dto.response;

import java.time.LocalDate;
import lombok.Data;

@Data
public class RecipeHistoryResponseDto {
    private Long id;
    private Long recipeId;
    private LocalDate date;
    private String name;
    private String description;
}
