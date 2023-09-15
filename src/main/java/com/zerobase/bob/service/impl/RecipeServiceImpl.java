package com.zerobase.bob.service.impl;

import com.zerobase.bob.dto.RecipeDto;
import com.zerobase.bob.entity.Recipe;
import com.zerobase.bob.entity.RecipeLink;
import com.zerobase.bob.entity.User;
import com.zerobase.bob.entity.type.RecipeType;
import com.zerobase.bob.exception.CustomException;
import com.zerobase.bob.exception.ErrorCode;
import com.zerobase.bob.repository.RecipeLinkRepository;
import com.zerobase.bob.repository.RecipeRepository;
import com.zerobase.bob.repository.UserRepository;
import com.zerobase.bob.service.AwsS3Service;
import com.zerobase.bob.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeLinkRepository recipeLinkRepository;
    private final AwsS3Service awsS3Service;

    private static final String SOURCE_URL = "http://localhost:8080/recipe/";
    @Override
    public List<RecipeDto> getMyRecipeList(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                                    new CustomException(ErrorCode.USER_NOT_FOUND));

        List<Recipe> recipeList = recipeRepository.findAllByUserId(user.getId());

        return RecipeDto.of(recipeList);
    }

    @Override
    public RecipeDto createRecipe(RecipeDto request, String email, MultipartFile file, String path) {

        String urlFilename = awsS3Service.uploadAndGetUrl(file, path);

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                                    new CustomException(ErrorCode.USER_NOT_FOUND));

        long count = recipeLinkRepository.findFirstByOrderByIdDesc().getId() + 1;
        RecipeLink recipeLink = new RecipeLink(SOURCE_URL + count, request.getName(), RecipeType.RECIPE_8080);
        recipeLinkRepository.save(recipeLink);

        Recipe recipe = Recipe.builder()
                .name(request.getName())
                .image(urlFilename)
                .description(request.getDescription())
                .cookTime(request.getCookTime())
                .ingredients(request.getIngredients())
                .steps(request.getSteps())
                .recipeLink(recipeLink.getLink())
                .userId(user.getId())
                .build();
        recipeRepository.save(recipe);

        return RecipeDto.of(recipe);
    }

    @Override
    public RecipeDto editRecipe(RecipeDto request, String email, MultipartFile file, String path) {

        String urlFilename = awsS3Service.uploadAndGetUrl(file, path);
        request.setImage(urlFilename);

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

        awsS3Service.deleteImageFromS3(recipe.getImage());
        recipeRepository.deleteById(recipeId);

        return true;
    }
}
