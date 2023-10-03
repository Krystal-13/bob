package com.zerobase.bob.entity;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Document(indexName = "recipes")
public class RecipeDocument {

    @Id
    private String id;
    private String name;
    private String image;
    private String description;
    private List<String> ingredients;
    private List<String> steps;
    private String cookTime;
    private String recipeLink;
    private List<Review> reviews;

    public static RecipeDocument ofEntity(Recipe recipe) {
        return RecipeDocument.builder()
                .id(recipe.getId().toString())
                .name(recipe.getName())
                .image(recipe.getImage())
                .description(recipe.getDescription())
                .ingredients(recipe.getIngredients())
                .steps(recipe.getSteps())
                .cookTime(recipe.getCookTime())
                .recipeLink(recipe.getRecipeLink())
                .reviews(recipe.getReviews())
                .build();
    }

}
