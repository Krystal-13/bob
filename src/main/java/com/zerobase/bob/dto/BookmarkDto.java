package com.zerobase.bob.dto;

import com.zerobase.bob.entity.Bookmark;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkDto {
    Long userId;
    Long recipeId;
    String groupName;

    public static BookmarkDto of(Bookmark bookmark) {
        return BookmarkDto.builder()
                .userId(bookmark.getUserId())
                .recipeId(bookmark.getRecipeId())
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
