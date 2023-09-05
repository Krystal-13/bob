package com.zerobase.bob.dto;

import com.zerobase.bob.entity.Bookmark;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class BookmarkDto {

    private Long id;
    private Long userId;
    private RecipeDto recipe;
    private String groupName;


    @Builder
    private BookmarkDto(Long userId, RecipeDto recipe, String groupName) {
        this.userId = userId;
        this.recipe = recipe;
        this.groupName = groupName;
    }

    public static BookmarkDto of(Bookmark bookmark) {
        return BookmarkDto.builder()
                .userId(bookmark.getUserId())
                .recipe(RecipeDto.of(bookmark.getRecipe()))
                .groupName(bookmark.getGroupName())
                .build();
    }

    public static List<BookmarkDto> of(List<Bookmark> bookmarkList) {

        if (CollectionUtils.isEmpty(bookmarkList)) {
            return Collections.emptyList();
        }

        List<BookmarkDto> list = new ArrayList<>();
        for (Bookmark x : bookmarkList) {
            list.add(BookmarkDto.of(x));
        }

        return list;
    }
}
