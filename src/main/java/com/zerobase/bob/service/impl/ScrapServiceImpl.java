package com.zerobase.bob.service.impl;

import com.zerobase.bob.dto.RecipeDto;
import com.zerobase.bob.entity.Recipe;
import com.zerobase.bob.entity.RecipeLink;
import com.zerobase.bob.entity.User;
import com.zerobase.bob.exception.CustomException;
import com.zerobase.bob.exception.ErrorCode;
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
    private final MenuLinkRepository menuLinkRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    @Override
    public List<RecipeLink> searchByMenuName(String menuName) {

        List<RecipeLink> recipeLinks = scraper.scrapRecipeUrlAndName(menuName);
        menuLinkRepository.saveAll(recipeLinks);

        return recipeLinks;
    }

    @Override
    public RecipeDto scrapByRecipeId(Long recipeId, String email) {

        RecipeLink recipeLink = menuLinkRepository.findById(recipeId)
                .orElseThrow(RuntimeException::new);

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                                 new CustomException(ErrorCode.USER_NOT_FOUND));

        Recipe recipe = scraper.scrapRecipe(recipeLink, user.getId());
        recipeRepository.save(recipe);

        return RecipeDto.of(recipe);
    }
}
