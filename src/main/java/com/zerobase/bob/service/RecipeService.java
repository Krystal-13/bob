package com.zerobase.bob.service;

import com.zerobase.bob.dto.RecipeDto;

import java.util.List;

public interface RecipeService {
    RecipeDto createRecipe(RecipeDto request, String email);

    RecipeDto editRecipe(RecipeDto request, String email);

    List<RecipeDto> getMyRecipeList(String email);

    boolean deleteRecipe(Long recipeId, String email);
}
