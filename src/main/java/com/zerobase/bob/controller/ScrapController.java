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

    @GetMapping("/search")
    public ResponseEntity<List<RecipeLink>> searchRecipe(
                                                @RequestParam
                                                        (value = "page", defaultValue = "1")
                                                        int page,
                                                @RequestParam String menuName) {

        return ResponseEntity.ok(scrapRecipe.searchByMenuName(menuName, page));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add")
    public ResponseEntity<RecipeDto> addRecipe(Principal principal,
                                               @RequestParam Long recipeLinkId,
                                               @RequestParam String groupName) {

        return ResponseEntity.ok(scrapRecipe.scrapByRecipeId(
                                    recipeLinkId, principal.getName(), groupName));
    }
}
