package com.example.cookbook.service;

import com.example.cookbook.model.Recipe;
import com.example.cookbook.model.RecipeHistory;
import java.util.List;

public interface RecipeService {
    Recipe getById(Long id);

    Recipe getByName(String name);

    void save(Recipe recipe);

    void saveChild(Recipe recipe, String parentRecipeName);

    void addChildToParent(String recipeName, String parentRecipeName);

    void update(Recipe recipe);

    List<Recipe> getAll(int page, int size);

    void delete(Long id);

    List<RecipeHistory> getHistory(int page, int size, String name);
}
