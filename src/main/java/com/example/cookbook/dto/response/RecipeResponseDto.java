package com.example.cookbook.dto.response;

import java.time.LocalDate;
import lombok.Data;

@Data
public class RecipeResponseDto {
    private Long id;
    private LocalDate date;
    private String name;
    private String description;
}
