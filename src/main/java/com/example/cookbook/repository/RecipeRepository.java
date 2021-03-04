package com.example.cookbook.repository;

import com.example.cookbook.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Recipe getByName(String name);
}
