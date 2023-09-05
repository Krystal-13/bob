package com.zerobase.bob.service;

import com.zerobase.bob.dto.RecipeDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RecipeService {
    RecipeDto createRecipe(RecipeDto request, String email, MultipartFile file);

    RecipeDto editRecipe(RecipeDto request, String email, MultipartFile file);

    List<RecipeDto> getMyRecipeList(String email);

    boolean deleteRecipe(Long recipeId, String email);
}
