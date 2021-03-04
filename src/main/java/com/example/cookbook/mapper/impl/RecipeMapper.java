package com.example.cookbook.mapper.impl;

import com.example.cookbook.dto.request.RecipeRequestDto;
import com.example.cookbook.dto.response.RecipeResponseDto;
import com.example.cookbook.mapper.MapperToDto;
import com.example.cookbook.mapper.MapperToEntity;
import com.example.cookbook.model.Recipe;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class RecipeMapper implements MapperToDto<Recipe, RecipeResponseDto>,
        MapperToEntity<Recipe, RecipeRequestDto> {
    @Override
    public RecipeResponseDto getDto(Recipe entity) {
        RecipeResponseDto recipeResponseDto = new RecipeResponseDto();
        recipeResponseDto.setId(entity.getId());
        recipeResponseDto.setName(entity.getName());
        recipeResponseDto.setDescription(entity.getDescription());
        recipeResponseDto.setDate(entity.getDate().toLocalDate());
        if (entity.getParentRecipe() != null) {
            recipeResponseDto.setParentRecipeName(entity.getParentRecipe().getName());
        }
        return recipeResponseDto;
    }

    @Override
    public Recipe getEntity(RecipeRequestDto dto) {
        Recipe recipe = new Recipe();
        recipe.setName(dto.getName());
        recipe.setDescription(dto.getDescription());
        recipe.setDate(LocalDateTime.now());
        return recipe;
    }
}
