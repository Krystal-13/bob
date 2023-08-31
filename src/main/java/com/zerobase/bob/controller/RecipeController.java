package com.zerobase.bob.controller;

import com.zerobase.bob.dto.RecipeDto;
import com.zerobase.bob.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping("/list")
    public ResponseEntity<?> getMyRecipeList(Principal principal) {
        List<RecipeDto> recipeList = recipeService.getMyRecipeList(principal.getName());

        return ResponseEntity.ok(recipeList);
    }

    @PostMapping("/new")
    public ResponseEntity<?> createRecipe(Principal principal, @RequestBody RecipeDto request) {
        RecipeDto recipeDto = recipeService.createRecipe(request, principal.getName());

        return ResponseEntity.ok(recipeDto);
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editRecipe(Principal principal, @RequestBody RecipeDto request) {
        RecipeDto recipeDto = recipeService.editRecipe(request, principal.getName());

        return ResponseEntity.ok(recipeDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteRecipe(Principal principal, @RequestParam Long RecipeId) {
        boolean result = recipeService.deleteRecipe(RecipeId, principal.getName());

        return ResponseEntity.ok(result);
    }
}
