package com.example.cookbook.repository;

import com.example.cookbook.model.Recipe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class RecipeRepositoryTest {
    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    public void get_Ok() {
        Recipe recipe = new Recipe();
        recipe.setName("Recipe");
        recipe.setDescription("Description");
        recipeRepository.save(recipe);
        Assertions.assertEquals("Recipe", recipeRepository.getByName("Recipe").get().getName());
        recipe.setDescription("new Description");
        recipeRepository.save(recipe);

        Recipe secondRecipe = new Recipe();
        secondRecipe.setName("Second Recipe");
        recipeRepository.save(secondRecipe);
        Assertions.assertEquals(recipeRepository.getByName("Recipe")
                .get().getDescription(), "new Description");
        Assertions.assertEquals(recipeRepository.getByName("Second Recipe").get(), secondRecipe);

        Assertions.assertEquals(recipeRepository.getByName("Recipe").get(), recipe);
    }
}
