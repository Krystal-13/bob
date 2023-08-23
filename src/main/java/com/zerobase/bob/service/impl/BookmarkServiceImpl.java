package com.zerobase.bob.service.impl;

import com.zerobase.bob.dto.RecipeDto;
import com.zerobase.bob.entity.Recipe;
import com.zerobase.bob.entity.User;
import com.zerobase.bob.exception.CustomException;
import com.zerobase.bob.exception.ErrorCode;
import com.zerobase.bob.repository.RecipeRepository;
import com.zerobase.bob.repository.UserRepository;
import com.zerobase.bob.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    @Override
    public List<RecipeDto> getMyRecipeList(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        List<Recipe> recipeList = recipeRepository.findAllByUserId(user.getId());

        return RecipeDto.of(recipeList);
    }

    @Override
    public RecipeDto createRecipe(RecipeDto request, String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Recipe recipe = Recipe.builder()
                .name(request.getName())
                .image(request.getImage())
                .description(request.getDescription())
                .cookTime(request.getCookTime())
                .ingredients(request.getIngredients())
                .steps(request.getSteps())
                .source(request.getSource())
                .userId(user.getId())
                .build();
        recipeRepository.save(recipe);

        return RecipeDto.of(recipe);
    }

    @Override
    public RecipeDto editRecipe(RecipeDto request, String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                                new CustomException(ErrorCode.USER_NOT_FOUND));

        Recipe oldRecipe = recipeRepository.findById(request.getId()).orElseThrow(() ->
                                        new CustomException(ErrorCode.RECIPE_NOT_FOUNT));

        if (!Objects.equals(user.getId(), oldRecipe.getUserId())) {
            throw new CustomException(ErrorCode.UNMATCHED_USER_RECIPE);
        }

        oldRecipe.updateRecipe(request);
        recipeRepository.save(oldRecipe);

        return RecipeDto.of(oldRecipe);
    }

    @Override
    public boolean deleteRecipe(Long recipeId, String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND));

        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() ->
                new CustomException(ErrorCode.RECIPE_NOT_FOUNT));

        if (!Objects.equals(user.getId(), recipe.getUserId())) {
            throw new CustomException(ErrorCode.UNMATCHED_USER_RECIPE);
        }

        recipeRepository.deleteById(recipeId);

        return true;
    }
}
