package com.example.cookbook.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.cookbook.dto.request.RecipeRequestDto;
import com.example.cookbook.model.Recipe;
import com.example.cookbook.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RecipeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RecipeService recipeService;

    @Test
    public void get_Ok() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/recipes"))
                .andExpect(status().isOk());
    }

    @Test
    public void addRecipe_Ok() throws Exception {
        RecipeRequestDto requestDto = new RecipeRequestDto();
        requestDto.setName("Recipe");
        requestDto.setDescription("Description");
        mockMvc.perform(MockMvcRequestBuilders.post("/recipes")
                .content(asJsonString(requestDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void getHistoryRecipe_Ok() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setName("recipe");
        recipeService.save(recipe);
        mockMvc.perform(MockMvcRequestBuilders.get("/recipes/history/recipe"))
                .andExpect(status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void addChild_Ok() throws Exception {
        Recipe parent = new Recipe();
        parent.setName("Parent");
        recipeService.save(parent);
        Recipe child = new Recipe();
        child.setName("Child");
        recipeService.save(child);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/recipes?childName=Child&parentName=Parent"))
                .andExpect(status().isOk());
    }
}
