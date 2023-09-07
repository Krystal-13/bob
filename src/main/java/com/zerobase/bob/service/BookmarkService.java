package com.zerobase.bob.service;

import com.zerobase.bob.dto.BookmarkDto;

import java.util.List;

public interface BookmarkService {

    List<BookmarkDto> getBookmarkListByGroupName(String email, String groupName);

    Boolean deleteBookmark(String email, Long bookmarkId);

    BookmarkDto editBookmark(String email, BookmarkDto bookmarkDto);

    BookmarkDto addBookmark(String email, Long recipeId, String groupName);

    BookmarkDto bookmarkMemo(String email, Long bookmarkId, String memo);
}
