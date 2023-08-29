package com.zerobase.bob.entity;

import com.zerobase.bob.dto.RecipeDto;
import com.zerobase.bob.entity.converter.IngredientConverter;
import com.zerobase.bob.entity.converter.StepConverter;
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

    @Convert(converter = IngredientConverter.class)
    private List<String> ingredients;

    @Convert(converter = StepConverter.class)
    private List<String> steps;

    private String cookTime;
    private String link;

    private Long userId;

    public void updateRecipe(RecipeDto request) {

        this.name = request.getName();
        this.image = request.getImage();
        this.description = request.getDescription();
        this.ingredients = request.getIngredients();
        this.steps = request.getSteps();
        this.cookTime = request.getCookTime();
        this.link = request.getLink();
        this.userId = request.getId();

    }
}
