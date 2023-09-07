package com.zerobase.bob.service.impl;

import com.zerobase.bob.dto.RecipeDto;
import com.zerobase.bob.entity.Recipe;
import com.zerobase.bob.entity.RecipeLink;
import com.zerobase.bob.exception.CustomException;
import com.zerobase.bob.exception.ErrorCode;
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

    @Override
    public List<RecipeLink> searchByMenuName(String menuName) {

        List<RecipeLink> recipeLinks = scraper.scrapRecipeUrlAndName(menuName);

        for (RecipeLink e : recipeLinks) {
            boolean exist = recipeLinkRepository.existsByLink(e.getLink());
            if (!exist) {
                recipeLinkRepository.save(e);
            }
        }

        return recipeLinks;
    }

    @Override
    public RecipeDto recipeDetail(Long recipeLinkId) {

        RecipeLink recipeLink = recipeLinkRepository.findById(recipeLinkId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_REQUEST));

        Optional<Recipe> optionalRecipe = recipeRepository.findByLink(recipeLink.getLink());

        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();
            return RecipeDto.of(recipe);
        }

        Recipe recipe = scraper.scrapRecipe(recipeLink);
        recipeRepository.save(recipe);

        return RecipeDto.of(recipe);
    }

}
