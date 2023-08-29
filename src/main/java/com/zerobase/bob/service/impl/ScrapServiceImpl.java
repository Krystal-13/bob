package com.zerobase.bob.service.impl;

import com.zerobase.bob.dto.RecipeDto;
import com.zerobase.bob.entity.Bookmark;
import com.zerobase.bob.entity.Recipe;
import com.zerobase.bob.entity.RecipeLink;
import com.zerobase.bob.entity.User;
import com.zerobase.bob.exception.CustomException;
import com.zerobase.bob.exception.ErrorCode;
import com.zerobase.bob.repository.BookmarkRepository;
import com.zerobase.bob.repository.MenuLinkRepository;
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
    private final MenuLinkRepository menuLinkRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    @Override
    public List<RecipeLink> searchByMenuName(String menuName, int page) {

        List<RecipeLink> recipeLinks = scraper.scrapRecipeUrlAndName(menuName, page);
        menuLinkRepository.saveAll(recipeLinks);

        return recipeLinks;
    }

    @Override
    public RecipeDto scrapByRecipeId(Long recipeId, String email, String groupName) {

        RecipeLink recipeLink = menuLinkRepository.findById(recipeId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_REQUEST));

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND));

        boolean result = recipeRepository.existsByUserIdAndSource(user.getId(), recipeLink.getLink());

        if (result) {
            throw new CustomException(ErrorCode.ALREADY_REGISTERED_RECIPE);
        }

        Recipe recipe = scraper.scrapRecipe(recipeLink, user.getId());
        recipeRepository.save(recipe);

        Bookmark bookmark = Bookmark.builder()
                                    .userId(user.getId())
                                    .recipeId(recipe.getId())
                                    .groupName(groupName)
                                    .build();
        bookmarkRepository.save(bookmark);

        return RecipeDto.of(recipe);
    }
}
