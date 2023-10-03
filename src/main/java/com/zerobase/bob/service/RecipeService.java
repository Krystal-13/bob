package com.zerobase.bob.service;

import com.zerobase.bob.dto.RecipeDto;
import com.zerobase.bob.entity.Recipe;
import com.zerobase.bob.entity.RecipeDocument;
import com.zerobase.bob.entity.RecipeLink;
import com.zerobase.bob.entity.User;
import com.zerobase.bob.entity.type.RecipeType;
import com.zerobase.bob.exception.CustomException;
import com.zerobase.bob.exception.ErrorCode;
import com.zerobase.bob.repository.RecipeLinkRepository;
import com.zerobase.bob.repository.RecipeRepository;
import com.zerobase.bob.repository.RecipeSearchRepository;
import com.zerobase.bob.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeLinkRepository recipeLinkRepository;
    private final AwsS3Service awsS3Service;
    private final RecipeSearchRepository recipeSearchRepository;

    @Value("${recipe.source.url}")
    private String sourceUrl;

    public List<RecipeDto> getMyRecipeList(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                                    new CustomException(ErrorCode.USER_NOT_FOUND));

        List<Recipe> recipeList = recipeRepository.findAllByUserId(user.getId());

        return RecipeDto.of(recipeList);
    }


    public RecipeDto createRecipe(RecipeDto request, String email, MultipartFile file, String path) {

        String urlFilename = awsS3Service.uploadAndGetUrl(file, path);

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                                    new CustomException(ErrorCode.USER_NOT_FOUND));

        long count = recipeLinkRepository.findFirstByOrderByIdDesc().getId() + 1;
        RecipeLink recipeLink = new RecipeLink(sourceUrl + count, request.getName(), RecipeType.RECIPE_8080);
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
        recipeSearchRepository.save(RecipeDocument.ofEntity(recipe));

        return RecipeDto.of(recipe);
    }
    @Transactional
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

        return RecipeDto.of(oldRecipe);
    }

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

    public Page<RecipeDto> searchRecipeByIngredient(String ingredient, Pageable pageable) {

        return recipeSearchRepository.findByIngredientsContains(ingredient, pageable).map(RecipeDto::ofDocument);
    }
}
