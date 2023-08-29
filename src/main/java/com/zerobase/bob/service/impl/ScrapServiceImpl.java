package com.zerobase.bob.service.impl;

import com.zerobase.bob.dto.RecipeDto;
import com.zerobase.bob.entity.Bookmark;
import com.zerobase.bob.entity.Recipe;
import com.zerobase.bob.entity.RecipeLink;
import com.zerobase.bob.entity.User;
import com.zerobase.bob.entity.type.RecipeType;
import com.zerobase.bob.exception.CustomException;
import com.zerobase.bob.exception.ErrorCode;
import com.zerobase.bob.repository.BookmarkRepository;
import com.zerobase.bob.repository.RecipeLinkRepository;
import com.zerobase.bob.repository.RecipeRepository;
import com.zerobase.bob.repository.UserRepository;
import com.zerobase.bob.scraper.Scraper;
import com.zerobase.bob.service.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScrapServiceImpl implements ScrapService {

    private final Scraper scraper;
    private final BookmarkRepository bookmarkRepository;
    private final RecipeLinkRepository recipeLinkRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    @Override
    public List<RecipeLink> searchByMenuName(String menuName, int page) {

        List<RecipeLink> recipeLinks = scraper.scrapRecipeUrlAndName(menuName, page);
        recipeLinkRepository.saveAll(recipeLinks);

        return recipeLinks;
    }

    @Override
    public RecipeDto scrapByRecipeId(Long recipeLinkId, String email, String groupName) {

        RecipeLink recipeLink = recipeLinkRepository.findById(recipeLinkId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_REQUEST));

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND));

        Recipe recipe = getRecipe(recipeLink, user.getId());
        duplicateCheck(recipe.getId(), user.getId());
        recipeRepository.save(recipe);

        Bookmark bookmark = Bookmark.builder()
                                    .userId(user.getId())
                                    .recipeId(recipe.getId())
                                    .groupName(groupName)
                                    .build();
        bookmarkRepository.save(bookmark);

        return RecipeDto.of(recipe);
    }

    private void duplicateCheck(Long recipeId, Long userId) {

        boolean result = bookmarkRepository.existsByUserIdAndRecipeId(userId, recipeId);

        if (result) {
            throw new CustomException(ErrorCode.ALREADY_REGISTERED_RECIPE);
        }
    }

    private Recipe getRecipe(RecipeLink recipeLink, Long userId) {

        Recipe recipe;

        if (RecipeType.RECIPE_8080.equals(recipeLink.getSource())) {
            recipe = recipeRepository.findByLink(recipeLink.getLink());
        } else {
            recipe = scraper.scrapRecipe(recipeLink, userId);
        }

        return recipe;
    }
}
