package com.zerobase.bob.service.impl;

import com.zerobase.bob.dto.BookmarkDto;
import com.zerobase.bob.entity.Bookmark;
import com.zerobase.bob.entity.User;
import com.zerobase.bob.exception.CustomException;
import com.zerobase.bob.exception.ErrorCode;
import com.zerobase.bob.repository.BookmarkRepository;
import com.zerobase.bob.repository.UserRepository;
import com.zerobase.bob.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;

    @Override
    public List<BookmarkDto> getBookmarkList(String email, String groupName) {

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                                    new CustomException(ErrorCode.USER_NOT_FOUND));

        List<Bookmark> bookmarkList = bookmarkRepository.findAllByUserIdAndGroupName(user.getId(), groupName);

        return BookmarkDto.of(bookmarkList);
    }

    @Override
    public Boolean deleteBookmark(String email, Long recipeId) {

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                                new CustomException(ErrorCode.USER_NOT_FOUND));

        Bookmark bookmark = bookmarkRepository.findByUserIdAndRecipeId(
                                                user.getId(), recipeId).orElseThrow(() ->
                                                    new CustomException(ErrorCode.RECIPE_NOT_FOUNT));
        bookmarkRepository.delete(bookmark);

        return true;
    }
}
