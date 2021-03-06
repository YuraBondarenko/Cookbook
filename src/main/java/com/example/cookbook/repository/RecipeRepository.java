package com.example.cookbook.repository;

import com.example.cookbook.model.Recipe;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Optional<Recipe> getByName(String name);
}
