package com.zerobase.bob.entity;

import com.zerobase.bob.dto.RecipeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String image;
    private String description;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> ingredients;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> steps;

    private String cookTime;
    private String source;

    private Long userId;

    public void updateRecipe(RecipeDto request) {

        this.name = request.getName();
        this.image = request.getImage();
        this.description = request.getDescription();
        this.ingredients = request.getIngredients();
        this.steps = request.getSteps();
        this.cookTime = request.getCookTime();
        this.source = request.getSource();
        this.userId = request.getId();

    }
}
