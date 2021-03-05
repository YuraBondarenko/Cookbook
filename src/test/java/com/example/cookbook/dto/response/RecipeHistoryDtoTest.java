package com.example.cookbook.dto.response;

import com.example.cookbook.mapper.impl.history.RecipeHistoryMapper;
import com.example.cookbook.model.Recipe;
import com.example.cookbook.model.RecipeHistory;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RecipeHistoryDtoTest {
    private static RecipeHistoryMapper recipeHistoryMapper;

    @BeforeAll
    static void beforeAll() {
        recipeHistoryMapper = new RecipeHistoryMapper();
    }

    @Test
    public void responseDto_Ok() {
        RecipeHistory recipe = new RecipeHistory();
        recipe.setName("recipe");
        recipe.setDate(LocalDateTime.now());

        RecipeHistoryResponseDto dto = recipeHistoryMapper.getDto(recipe);
        Assertions.assertEquals("recipe", dto.getName());
        Assertions.assertEquals(recipe.getDate().toLocalDate(), dto.getDate());
    }

    @Test
    public void entity_Ok() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Recipe");

        RecipeHistory historyEntity = recipeHistoryMapper.getHistoryEntity(recipe);
        Assertions.assertEquals("Recipe", historyEntity.getName());
        Assertions.assertEquals(1L, historyEntity.getRecipeId());
    }
}
