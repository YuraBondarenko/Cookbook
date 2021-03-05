package com.example.cookbook.service.impl;

import com.example.cookbook.mapper.impl.history.RecipeHistoryMapper;
import com.example.cookbook.model.Recipe;
import com.example.cookbook.model.RecipeHistory;
import com.example.cookbook.repository.RecipeHistoryRepository;
import com.example.cookbook.repository.RecipeRepository;
import com.example.cookbook.service.RecipeService;
import exception.ParentNotAllowedException;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeHistoryRepository recipeHistoryRepository;
    private final RecipeHistoryMapper recipeHistoryMapper;

    @Override
    public Recipe getById(Long id) {
        return recipeRepository.getOne(id);
    }

    @Override
    public Recipe getByName(String name) {
        return recipeRepository.getByName(name).orElseThrow(()
                -> new EntityNotFoundException("Cannot find recipe with name" + name));
    }

    @Override
    public void save(Recipe recipe) {
        recipe.setDate(LocalDateTime.now());
        recipeRepository.save(recipe);
        recipeHistoryRepository.save(recipeHistoryMapper.getHistoryEntity(recipe));
    }

    @Override
    public void update(Recipe recipe) {
        recipeHistoryRepository.save(recipeHistoryMapper.getHistoryEntity(recipe));
        recipeRepository.save(recipe);
    }

    @Override
    public void saveChild(Recipe recipe, String parentRecipeName) {
        recipe.setParentRecipe(getByName(parentRecipeName));
        recipe.setDate(LocalDateTime.now());
        recipeRepository.save(recipe);
        recipeHistoryRepository.save(recipeHistoryMapper.getHistoryEntity(recipe));
    }

    @Override
    public void addChildToParent(String recipeName, String parentRecipeName)
            throws ParentNotAllowedException {
        Recipe recipe = getByName(recipeName);
        Recipe parentRecipe = getByName(parentRecipeName);
        Recipe parent = parentRecipe.getParentRecipe();
        while (parent != null) {
            if (parent.equals(recipe)) {
                throw new ParentNotAllowedException("Cannot add parent recipe '"
                        + parentRecipe.getName()
                        + "' to child recipe '" + recipe.getName() + "'");
            }
            parent = parent.getParentRecipe();
        }
        recipe.setParentRecipe(parentRecipe);
        recipeHistoryRepository.save(recipeHistoryMapper.getHistoryEntity(recipe));
        recipeRepository.save(recipe);
    }

    @Override
    public List<Recipe> getAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return recipeRepository.findAll(pageable).getContent();
    }

    @Override
    public void delete(Long id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public List<RecipeHistory> getHistory(int page, int size, String name) {
        Pageable pageable = PageRequest.of(page, size);
        return recipeHistoryRepository.findAllByName(name, pageable);
    }
}
