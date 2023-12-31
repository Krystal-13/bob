package com.zerobase.bob.dto;

import com.zerobase.bob.entity.Recipe;
import com.zerobase.bob.entity.RecipeDocument;
import com.zerobase.bob.entity.Review;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class RecipeDto {

  private Long id;
  private String name;
  private String image;
  private String description;
  private List<String> ingredients;
  private List<String> steps;
  private String cookTime;
  private String recipeLink;
  private List<ReviewDto> reviews;

  public void setImage(String image) {
    this.image = image;
  }

  @Builder
  public RecipeDto(Long id, String name, String image, String description, List<String> ingredients, List<String> steps, String cookTime, String recipeLink, List<ReviewDto> reviews) {
    this.id = id;
    this.name = name;
    this.image = image;
    this.description = description;
    this.ingredients = ingredients;
    this.steps = steps;
    this.cookTime = cookTime;
    this.recipeLink = recipeLink;
    this.reviews = reviews;
  }

  public static RecipeDto of(Recipe recipe) {
    return RecipeDto.builder()
        .id(recipe.getId())
        .name(recipe.getName())
        .image(recipe.getImage())
        .description(recipe.getDescription())
        .ingredients(recipe.getIngredients())
        .steps(recipe.getSteps())
        .cookTime(recipe.getCookTime())
        .recipeLink(recipe.getRecipeLink())
        .reviews(getReviewText(recipe.getReviews()))
        .build();
  }

  public static RecipeDto ofDocument(RecipeDocument recipe) {
    return RecipeDto.builder()
            .id(Long.valueOf(recipe.getId()))
            .name(recipe.getName())
            .image(recipe.getImage())
            .description(recipe.getDescription())
            .ingredients(recipe.getIngredients())
            .steps(recipe.getSteps())
            .cookTime(recipe.getCookTime())
            .recipeLink(recipe.getRecipeLink())
            .reviews(getReviewText(recipe.getReviews()))
            .build();
  }

  public static List<RecipeDto> of(List<Recipe> recipeList) {
    if (recipeList == null) {
      return Collections.emptyList();
    }

    List<RecipeDto> recipeDtolist = new ArrayList<>();
    for (Recipe x : recipeList) {
      recipeDtolist.add(RecipeDto.of(x));
    }

    return recipeDtolist;
  }

  private static List<ReviewDto> getReviewText(List<Review> reviews) {

    if (reviews == null) {
      return Collections.emptyList();
    }

    List<ReviewDto> reviewList = new ArrayList<>();
    for(Review x : reviews) {
      ReviewDto review = ReviewDto.builder()
              .userName(x.getUser().getName())
              .text(x.getText())
              .image((x.getImage()))
              .score(x.getScore())
              .registeredAt(x.getRegisteredAt())
              .build();
      reviewList.add(review);
    }
    return reviewList;
  }
}
