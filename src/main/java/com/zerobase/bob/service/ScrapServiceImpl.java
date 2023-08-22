package com.zerobase.bob.service;

import com.zerobase.bob.dto.RecipeDto;
import com.zerobase.bob.entity.Recipe;
import com.zerobase.bob.entity.RecipeLink;
import com.zerobase.bob.repository.MenuLinkRepository;
import com.zerobase.bob.repository.RecipeRepository;
import com.zerobase.bob.scraper.Scraper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScrapServiceImpl implements ScrapService{

    private final Scraper scraper;
    private final MenuLinkRepository menuLinkRepository;
    private final RecipeRepository recipeRepository;
    @Override
    public List<RecipeLink> searchByMenuName(String menuName) {

        List<RecipeLink> recipeLinks = scraper.scrapRecipeUrlAndName(menuName);
        menuLinkRepository.saveAll(recipeLinks);

        return recipeLinks;
    }

    @Override
    public RecipeDto scrapByRecipeId(Long recipeId) {

        RecipeLink recipeLink = menuLinkRepository.findById(recipeId)
                .orElseThrow(RuntimeException::new);

        Recipe recipe = scraper.scrapRecipe(recipeLink);
        recipeRepository.save(recipe);

        return RecipeDto.of(recipe);
    }
}
