package com.zerobase.bob.dto;

import com.zerobase.bob.entity.Ingredient;
import com.zerobase.bob.entity.Recipe;
import java.time.LocalTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDto {

  private Long id;
  private String name;
  private String description;
  private List<Ingredient> ingredients;
  private List<String> steps;
  private String cookTime;

  public static RecipeDto of(Recipe recipe) {
    return RecipeDto.builder()
        .name(recipe.getName())
        .description(recipe.getDescription())
        .ingredients(recipe.getIngredients())
        .steps(recipe.getSteps())
        .cookTime(recipe.getCookTime())
        .build();
  }

}
