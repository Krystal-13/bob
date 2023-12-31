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
    private String memo;


    @Builder
    private BookmarkDto(Long id, Long userId, RecipeDto recipe, String groupName, String memo) {
        this.id = id;
        this.userId = userId;
        this.recipe = recipe;
        this.groupName = groupName;
        this.memo = memo;
    }

    public static BookmarkDto of(Bookmark bookmark) {
        return BookmarkDto.builder()
                .id(bookmark.getId())
                .userId(bookmark.getUserId())
                .recipe(RecipeDto.of(bookmark.getRecipe()))
                .groupName(bookmark.getGroupName())
                .memo(bookmark.getMemo())
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
