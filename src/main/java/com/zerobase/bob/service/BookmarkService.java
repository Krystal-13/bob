package com.zerobase.bob.service;

import com.zerobase.bob.dto.BookmarkDto;

import java.util.List;

public interface BookmarkService {

    List<BookmarkDto>  getBookmarkList(String email, String groupName);

    Boolean deleteBookmark(String email, Long recipeId);
}
