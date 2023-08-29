package com.zerobase.bob.controller;

import com.zerobase.bob.dto.BookmarkDto;
import com.zerobase.bob.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;
    @GetMapping("/list")
    public ResponseEntity<List<BookmarkDto>> searchRecipe(Principal principal,
                                                          @RequestParam String groupName) {

        return ResponseEntity.ok(bookmarkService.getBookmarkList(principal.getName(), groupName));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteBookmark(Principal principal,
                                                          @RequestParam Long recipeId) {

        return ResponseEntity.ok(bookmarkService.deleteBookmark(principal.getName(), recipeId));
    }

}
