package com.zerobase.bob.controller;

import com.zerobase.bob.dto.RecipeDto;
import com.zerobase.bob.entity.RecipeLink;
import com.zerobase.bob.service.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScrapController {

    private final ScrapService scrapRecipe;

    @GetMapping("/search")
    public ResponseEntity<List<RecipeLink>> searchRecipe(
                                                @RequestParam
                                                        (value = "page", defaultValue = "1")
                                                        int page,
                                                @RequestParam String menuName) {

        return ResponseEntity.ok(scrapRecipe.searchByMenuName(menuName, page));
    }

    @GetMapping("/detail/{recipeLinkId}")
    public ResponseEntity<RecipeDto> recipeDetail(@PathVariable Long recipeLinkId) {

        return ResponseEntity.ok(scrapRecipe.recipeDetail(recipeLinkId));
    }
}
