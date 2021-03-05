package com.example.cookbook.controller;

import com.example.cookbook.dto.request.RecipeRequestDto;
import com.example.cookbook.dto.response.RecipeHistoryResponseDto;
import com.example.cookbook.dto.response.RecipeResponseDto;
import com.example.cookbook.mapper.impl.RecipeMapper;
import com.example.cookbook.mapper.impl.history.RecipeHistoryMapper;
import com.example.cookbook.model.Recipe;
import com.example.cookbook.service.RecipeService;
import exception.ParentNotAllowedException;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/recipes")
public class RecipeController {
    private final RecipeService recipeService;
    private final RecipeMapper recipeMapper;
    private final RecipeHistoryMapper recipeHistoryMapper;

    @GetMapping
    public ResponseEntity<List<RecipeResponseDto>> getAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false, defaultValue = "name") String sortBy) {
        List<RecipeResponseDto> recipes = recipeService.getAll(page, limit, sortBy).stream()
                .map(recipeMapper::getDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @GetMapping("/history/{name}")
    public ResponseEntity<List<RecipeHistoryResponseDto>> getHistoryByName(
            @PathVariable String name,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int limit) {
        List<RecipeHistoryResponseDto> recipesHistory = recipeService
                .getHistory(page, limit, name).stream()
                .map(recipeHistoryMapper::getDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(recipesHistory, HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<RecipeResponseDto> getByName(@PathVariable String name) {
        return new ResponseEntity<>(recipeMapper.getDto(recipeService.getByName(name)),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RecipeResponseDto> save(
            @RequestBody @Valid RecipeRequestDto recipeRequestDto) {
        Recipe recipe = recipeMapper.getEntity(recipeRequestDto);
        recipeService.save(recipe);
        return new ResponseEntity<>(recipeMapper.getDto(recipe),
                HttpStatus.CREATED);
    }

    @PostMapping("/{name}")
    public ResponseEntity<RecipeResponseDto> saveChild(
            @PathVariable String name,
            @RequestBody @Valid RecipeRequestDto recipeRequestDto) {
        Recipe recipe = recipeMapper.getEntity(recipeRequestDto);
        recipeService.saveChild(recipe, name);
        return new ResponseEntity<>(recipeMapper.getDto(recipe), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<RecipeResponseDto> addChildToParent(
            @RequestParam String childName,
            @RequestParam String parentName) throws ParentNotAllowedException {
        recipeService.addChildToParent(childName, parentName);
        return new ResponseEntity<>(recipeMapper.getDto(recipeService.getByName(childName)),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeResponseDto> update(
            @PathVariable Long id,
            @RequestBody @Valid RecipeRequestDto recipeRequestDto) {
        Recipe entity = recipeMapper.getEntity(recipeRequestDto);
        entity.setId(id);
        recipeService.update(entity);
        return new ResponseEntity<>(recipeMapper.getDto(entity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RecipeResponseDto> delete(@PathVariable Long id) {
        recipeService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
