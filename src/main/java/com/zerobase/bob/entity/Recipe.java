package com.zerobase.bob.entity;

import com.zerobase.bob.dto.RecipeDto;
import com.zerobase.bob.entity.converter.IngredientConverter;
import com.zerobase.bob.entity.converter.StepConverter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String image;
    private String description;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = IngredientConverter.class)
    private List<String> ingredients;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = StepConverter.class)
    private List<String> steps;

    private String cookTime;
    private String recipeLink;

    private Long userId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id")
    private List<Review> reviews = new ArrayList<>();

    @Builder
    public Recipe(String name, String image, String description, List<String> ingredients, List<String> steps, String cookTime, String recipeLink, Long userId, List<Review> reviews) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.ingredients = ingredients;
        this.steps = steps;
        this.cookTime = cookTime;
        this.recipeLink = recipeLink;
        this.userId = userId;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public void updateRecipe(RecipeDto request) {

        this.name = request.getName();
        this.image = request.getImage();
        this.description = request.getDescription();
        this.ingredients = request.getIngredients();
        this.steps = request.getSteps();
        this.cookTime = request.getCookTime();

    }
}
