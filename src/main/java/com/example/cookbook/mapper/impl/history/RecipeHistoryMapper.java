package com.example.cookbook.mapper.impl.history;

import com.example.cookbook.dto.response.RecipeHistoryResponseDto;
import com.example.cookbook.mapper.HistoryMapperToEntity;
import com.example.cookbook.mapper.MapperToDto;
import com.example.cookbook.model.Recipe;
import com.example.cookbook.model.RecipeHistory;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class RecipeHistoryMapper implements HistoryMapperToEntity<RecipeHistory, Recipe>,
        MapperToDto<RecipeHistory, RecipeHistoryResponseDto> {
    @Override
    public RecipeHistory getHistoryEntity(Recipe recipe) {
        RecipeHistory recipeHistory = new RecipeHistory();
        recipeHistory.setId(recipeHistory.getId());
        recipeHistory.setRecipeId(recipe.getId());
        recipeHistory.setName(recipe.getName());
        recipeHistory.setDescription(recipe.getDescription());
        recipeHistory.setDate(LocalDateTime.now());
        recipeHistory.setParentRecipe(recipe.getParentRecipe());
        return recipeHistory;
    }

    @Override
    public RecipeHistoryResponseDto getDto(RecipeHistory entity) {
        RecipeHistoryResponseDto dto = new RecipeHistoryResponseDto();
        dto.setRecipeId(entity.getRecipeId());
        dto.setDescription(entity.getDescription());
        dto.setDate(entity.getDate().toLocalDate());
        dto.setName(entity.getName());
        dto.setId(entity.getId());
        if (entity.getParentRecipe() != null) {
            dto.setParentRecipeName(entity.getParentRecipe().getName());
        }
        return dto;
    }
}
