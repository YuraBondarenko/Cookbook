package com.example.cookbook.controller;

import com.example.cookbook.model.Recipe;
import com.example.cookbook.service.RecipeService;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class DataInjection {
    private final RecipeService recipeService;

    @PostConstruct
    public void inject() {
        Recipe recipeParent = new Recipe();
        recipeParent.setName("Recipe parent");
        recipeParent.setDescription("1");
        recipeService.save(recipeParent);
        recipeParent.setDescription("2");
        recipeService.update(recipeParent);
        recipeParent.setDescription("3");
        recipeService.update(recipeParent);

        Recipe recipeChild = new Recipe();
        recipeChild.setName("Recipe child");
        recipeChild.setDescription("1");
        recipeService.save(recipeChild);
        recipeChild.setParentRecipe(recipeParent);
        recipeService.update(recipeChild);

        Recipe newRecipeChild = new Recipe();
        newRecipeChild.setName("new Recipe child");
        recipeService.saveChild(newRecipeChild, recipeChild.getName());
    }
}
