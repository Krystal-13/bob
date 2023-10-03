package com.zerobase.bob.service;

import com.zerobase.bob.dto.RecipeDto;
import com.zerobase.bob.entity.MenuName;
import com.zerobase.bob.entity.Recipe;
import com.zerobase.bob.entity.RecipeDocument;
import com.zerobase.bob.entity.RecipeLink;
import com.zerobase.bob.exception.CustomException;
import com.zerobase.bob.exception.ErrorCode;
import com.zerobase.bob.repository.MenuNameRepository;
import com.zerobase.bob.repository.RecipeLinkRepository;
import com.zerobase.bob.repository.RecipeRepository;
import com.zerobase.bob.repository.RecipeSearchRepository;
import com.zerobase.bob.scraper.Scraper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScrapService {

    private final Scraper scraper;
    private final RecipeLinkRepository recipeLinkRepository;
    private final RecipeRepository recipeRepository;
    private final MenuNameRepository menuNameRepository;
    private final RecipeSearchRepository recipeSearchRepository;

    public List<RecipeLink> searchByMenuName(String menuName) {

        boolean exists = menuNameRepository.existsById(menuName);
        if (!exists) {
            menuNameRepository.save(new MenuName(menuName));
            List<RecipeLink> recipeLinks = scraper.scrapRecipeUrlAndName(menuName);
            recipeLinkRepository.saveAll(recipeLinks);
        }


        return recipeLinkRepository.findByNameContains(menuName);
    }

    public RecipeDto recipeDetail(Long recipeLinkId) {

        RecipeLink recipeLink = recipeLinkRepository.findById(recipeLinkId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_REQUEST));

        Optional<Recipe> optionalRecipe = recipeRepository.findByRecipeLink(recipeLink.getLink());

        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();
            return RecipeDto.of(recipe);
        }

        Recipe recipe = scraper.scrapRecipe(recipeLink);
        recipeRepository.save(recipe);
        recipeSearchRepository.save(RecipeDocument.ofEntity(recipe));

        return RecipeDto.of(recipe);
    }

}
