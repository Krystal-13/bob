package com.zerobase.bob.service;

import com.zerobase.bob.dto.RecipeDto;
import com.zerobase.bob.entity.RecipeLink;

import java.util.List;

public interface ScrapService {
    List<RecipeLink> searchByMenuName(String menuName, int page);

    RecipeDto scrapByRecipeId(Long recipeId, String email, String groupName);
}
