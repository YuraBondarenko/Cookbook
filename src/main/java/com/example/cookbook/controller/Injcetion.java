package com.example.cookbook.controller;

import com.example.cookbook.model.Recipe;
import com.example.cookbook.service.RecipeService;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class Injcetion {
    private final RecipeService recipeService;

    @PostConstruct
    public void inject() {
        Recipe recipe = new Recipe();
        recipe.setName("name");
        recipe.setDescription("1");
        recipeService.save(recipe);
        recipe.setDescription("2");
        recipeService.update(recipe);
        recipe.setDescription("3");
        recipeService.update(recipe);

        Recipe recipeChile = new Recipe();
        recipeChile.setName("child");
        recipeChile.setDescription("1");
        recipeChile.setParentRecipe(recipe);
        recipeService.save(recipeChile);
    }
}
