package com.zerobase.bob.controller;

import com.zerobase.bob.dto.RecipeDto;
import com.zerobase.bob.entity.RecipeLink;
import com.zerobase.bob.service.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScrapController {

    private final ScrapService scrapRecipe;


    @GetMapping("/search/{menuName}")
    public ResponseEntity<?> searchRecipe(@PathVariable String menuName) {
        List<RecipeLink> recipeLinks = scrapRecipe.searchByMenuName(menuName);

        return ResponseEntity.ok(recipeLinks);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add")
    public ResponseEntity<?> addRecipe(Principal principal, @RequestParam Long recipeId) {
        RecipeDto recipeDto = scrapRecipe.scrapByRecipeId(recipeId, principal.getName());

        return ResponseEntity.ok(recipeDto);
    }
}
