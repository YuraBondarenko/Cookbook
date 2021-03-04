package com.example.cookbook.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "recipes_history")
public class RecipeHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "recipe_id")
    private Long recipeId;
    @Column(nullable = false)
    private String name;
    private String description;
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "parent_recipe")
    private Recipe parentRecipe;
}
