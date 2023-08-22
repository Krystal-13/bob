package com.zerobase.bob.dto;

import com.zerobase.bob.entity.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDto {

  private Long id;
  private String name;
  private String image;
  private String description;
  private List<String> ingredients;
  private List<String> steps;
  private String cookTime;
  private String source;

  public static RecipeDto of(Recipe recipe) {
    return RecipeDto.builder()
        .name(recipe.getName())
        .image(recipe.getImage())
        .description(recipe.getDescription())
        .ingredients(recipe.getIngredients())
        .steps(recipe.getSteps())
        .cookTime(recipe.getCookTime())
        .source(recipe.getSource())
        .build();
  }

}
