package com.zerobase.bob.service.impl;

import com.zerobase.bob.dto.RecipeDto;
import com.zerobase.bob.entity.Recipe;
import com.zerobase.bob.entity.RecipeLink;
import com.zerobase.bob.exception.CustomException;
import com.zerobase.bob.exception.ErrorCode;
import com.zerobase.bob.repository.MenuNameRepository;
import com.zerobase.bob.repository.RecipeLinkRepository;
import com.zerobase.bob.repository.RecipeRepository;
import com.zerobase.bob.scraper.Scraper;
import com.zerobase.bob.service.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScrapServiceImpl implements ScrapService {

    private final Scraper scraper;
    private final RecipeLinkRepository recipeLinkRepository;
    private final RecipeRepository recipeRepository;
    private final MenuNameRepository menuNameRepository;

    @Override
    public List<RecipeLink> searchByMenuName(String menuName) {

        boolean exists = menuNameRepository.existsById(menuName);
        if (!exists) {
            List<RecipeLink> recipeLinks = scraper.scrapRecipeUrlAndName(menuName);
            recipeLinkRepository.saveAll(recipeLinks);
        }

        String name = String.format("%%%s%%",menuName);

        return recipeLinkRepository.findByNameLike(name);
    }

    @Override
    public RecipeDto recipeDetail(Long recipeLinkId) {

        RecipeLink recipeLink = recipeLinkRepository.findById(recipeLinkId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_REQUEST));

        Optional<Recipe> optionalRecipe = recipeRepository.findByRecipeLinkId(recipeLinkId);

        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();
            return RecipeDto.of(recipe);
        }

        Recipe recipe = scraper.scrapRecipe(recipeLink);
        recipeRepository.save(recipe);

        return RecipeDto.of(recipe);
    }

}
