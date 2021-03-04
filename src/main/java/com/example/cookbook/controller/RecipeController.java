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
import lombok.AllArgsConstructor;
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
    public List<RecipeResponseDto> getAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false, defaultValue = "name") String sortBy) {
        return recipeService.getAll(page, limit, sortBy).stream()
                .map(recipeMapper::getDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/history/{name}")
    public List<RecipeHistoryResponseDto> getHistoryByName(
            @PathVariable String name,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int limit) {
        return recipeService.getHistory(page, limit, name).stream()
                .map(recipeHistoryMapper::getDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{name}")
    public RecipeResponseDto getByName(@PathVariable String name) {
        return recipeMapper.getDto(recipeService.getByName(name));
    }

    @PostMapping
    public void save(@RequestBody RecipeRequestDto recipeRequestDto) {
        recipeService.save(recipeMapper.getEntity(recipeRequestDto));
    }

    @PostMapping("/{name}")
    public void saveChild(@PathVariable String name,
                          @RequestBody RecipeRequestDto recipeRequestDto) {
        recipeService.saveChild(recipeMapper.getEntity(recipeRequestDto), name);
    }

    @PutMapping
    public void addChildToParent(@RequestParam String childName,
                                 @RequestParam String parentName) throws ParentNotAllowedException {
        recipeService.addChildToParent(childName, parentName);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id,
                       @RequestBody RecipeRequestDto recipeRequestDto) {
        Recipe entity = recipeMapper.getEntity(recipeRequestDto);
        entity.setId(id);
        recipeService.update(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        recipeService.delete(id);
    }
}
