package com.zerobase.bob.controller;

import com.zerobase.bob.dto.BookmarkDto;
import com.zerobase.bob.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping("/add")
    public ResponseEntity<BookmarkDto> addBookmark(Principal principal,
                                               @RequestParam Long recipeId,
                                               @RequestParam String groupName) {

        return ResponseEntity.ok(bookmarkService.addBookmark(principal.getName(), recipeId, groupName));
    }
    @GetMapping("/list")
    public ResponseEntity<List<BookmarkDto>> bookmarkList(Principal principal,
                                                          @RequestParam String groupName) {

        return ResponseEntity.ok(bookmarkService.getBookmarkListByGroupName(principal.getName(), groupName));
    }

    @PatchMapping("/edit")
    public ResponseEntity<BookmarkDto> editBookmarkName(Principal principal,
                                                        @RequestBody BookmarkDto bookmarkDto) {

        return ResponseEntity.ok(bookmarkService.editBookmark(principal.getName(), bookmarkDto));
    }

    @PutMapping("/memo")
    public ResponseEntity<BookmarkDto> bookmarkMemo(Principal principal,
                                                    @RequestParam Long bookmarkId, @RequestBody Map<String, String> memo) {

        return ResponseEntity.ok(bookmarkService.bookmarkMemo(principal.getName(), bookmarkId, memo.get("memo")));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteBookmark(Principal principal,
                                                          @RequestParam Long recipeId) {

        return ResponseEntity.ok(bookmarkService.deleteBookmark(principal.getName(), recipeId));
    }

}
