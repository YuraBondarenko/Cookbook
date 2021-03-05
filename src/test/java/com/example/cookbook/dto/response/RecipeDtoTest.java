package com.example.cookbook.dto.response;

import com.example.cookbook.dto.request.RecipeRequestDto;
import com.example.cookbook.mapper.impl.RecipeMapper;
import com.example.cookbook.model.Recipe;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

@JsonTest
public class RecipeDtoTest {
    private static RecipeMapper recipeMapper;

    @BeforeAll
    public static void beforeAll() {
        recipeMapper = new RecipeMapper();
    }

    @Test
    public void responseDto_Ok() {
        Recipe recipe = new Recipe();
        recipe.setName("recipe");
        recipe.setDate(LocalDateTime.now());

        RecipeResponseDto dto = recipeMapper.getDto(recipe);
        Assertions.assertEquals("recipe", dto.getName());
        Assertions.assertEquals(recipe.getDate().toLocalDate(), dto.getDate());
    }

    @Test
    public void requestDto_Ok() {
        RecipeRequestDto requestDto = new RecipeRequestDto();
        requestDto.setName("recipe");
        requestDto.setDescription("description");
        Recipe entity = recipeMapper.getEntity(requestDto);
        Assertions.assertEquals("recipe", entity.getName());
        Assertions.assertEquals("description", entity.getDescription());
    }

    @Test
    public void entity_Ok() {
        Recipe recipe = new Recipe();
        recipe.setName("recipe");
        recipe.setDate(LocalDateTime.now());

        Recipe childRecipe = new Recipe();
        childRecipe.setName("child");
        childRecipe.setParentRecipe(recipe);

        Assertions.assertEquals("recipe", childRecipe.getParentRecipe().getName());
        Assertions.assertEquals(recipe.getDate().toLocalDate(), childRecipe
                .getParentRecipe().getDate().toLocalDate());
        Assertions.assertEquals(recipe, childRecipe.getParentRecipe());
    }
}
