package com.zerobase.bob.controller;

import com.zerobase.bob.dto.RecipeDto;
import com.zerobase.bob.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping("/list")
    public ResponseEntity<List<RecipeDto>> getMyRecipeList(Principal principal) {

        return ResponseEntity.ok(recipeService.getMyRecipeList(principal.getName()));
    }

    @PostMapping("/new")
    public ResponseEntity<RecipeDto> createRecipe(Principal principal,
                                                  @RequestPart(value = "dto") RecipeDto request,
                                                  @RequestPart(value = "file") MultipartFile file) {

        return ResponseEntity.ok(recipeService.createRecipe(request, principal.getName(), file));
    }

    @PatchMapping("/edit")
    public ResponseEntity<RecipeDto> editRecipe(Principal principal,
                                                @RequestPart(value = "dto") RecipeDto request,
                                                @RequestPart(value = "file") MultipartFile file) {

        return ResponseEntity.ok(recipeService.editRecipe(request, principal.getName(), file));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteRecipe(Principal principal, @RequestParam Long RecipeId) {

        return ResponseEntity.ok(recipeService.deleteRecipe(RecipeId, principal.getName()));
    }
}
