package com.example.cookbook.service.impl;

import com.example.cookbook.model.Recipe;
import com.example.cookbook.model.RecipeHistory;
import com.example.cookbook.service.RecipeService;
import exception.ParentNotAllowedException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class RecipeServiceImplTest {
    @Autowired
    private RecipeService recipeService;

    @BeforeEach
    public void setUp() {
        Recipe recipe = new Recipe();
        recipe.setName("Recipe");
        recipe.setDescription("Description");
        recipeService.save(recipe);

        Recipe childRecipe = new Recipe();
        childRecipe.setName("Child Recipe");
        recipeService.saveChild(childRecipe, recipe.getName());
        childRecipe.setDescription("Description");
        recipeService.update(childRecipe);
        System.out.println(recipe.getId() + " " + childRecipe.getId());
    }

    @Test
    public void getHistory_Ok() {
        List<RecipeHistory> recipe = recipeService.getHistory(0, 10, "Recipe");
        Assertions.assertEquals(1, recipe.size());
        List<RecipeHistory> childRecipe = recipeService.getHistory(0, 10, "Child Recipe");
        Assertions.assertEquals(2, childRecipe.size());
        Assertions.assertEquals("Child Recipe", childRecipe.get(0).getName());
        Assertions.assertEquals("Recipe", childRecipe.get(1).getParentRecipe().getName());
    }

    @Test
    public void get_Ok() {
        Assertions.assertEquals("Description", recipeService.getByName("Recipe").getDescription());
        List<Recipe> recipes = recipeService.getAll(0, 10, "name");
        Assertions.assertEquals(2L, recipes.size());
    }

    @Test
    public void updateAndDelete_Ok() {
        Recipe recipe = recipeService.getByName("Recipe");
        recipe.setDescription("New Description");
        recipeService.update(recipe);
        recipeService.delete(recipeService.getByName("Child Recipe").getId());
        Assertions.assertEquals("New Description", recipeService.getByName("Recipe")
                .getDescription());
        Assertions.assertEquals(1, recipeService.getAll(0, 10, "name")
                .size());
    }

    @Test
    public void addChild_Ok() throws ParentNotAllowedException {
        Recipe recipe = recipeService.getByName("Child Recipe");

        Recipe childRecipe = new Recipe();
        childRecipe.setName("Child");
        recipeService.save(childRecipe);

        recipeService.addChildToParent(childRecipe.getName(), recipe.getName());

        Assertions.assertEquals(recipe, childRecipe.getParentRecipe());
    }

    @Test
    void addChild_NotOk() {
        Recipe recipe = recipeService.getByName("Recipe");
        Recipe childRecipe = recipeService.getByName("Child Recipe");
        Assertions.assertThrows(ParentNotAllowedException.class, () -> {
            recipeService.addChildToParent(recipe.getName(), childRecipe.getName());
        });
    }
}
