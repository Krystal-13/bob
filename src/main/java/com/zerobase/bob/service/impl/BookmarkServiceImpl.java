package com.zerobase.bob.service.impl;

import com.zerobase.bob.dto.BookmarkDto;
import com.zerobase.bob.entity.Bookmark;
import com.zerobase.bob.entity.Recipe;
import com.zerobase.bob.entity.User;
import com.zerobase.bob.exception.CustomException;
import com.zerobase.bob.exception.ErrorCode;
import com.zerobase.bob.repository.BookmarkRepository;
import com.zerobase.bob.repository.RecipeRepository;
import com.zerobase.bob.repository.UserRepository;
import com.zerobase.bob.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    @Override
    public List<BookmarkDto> getBookmarkListByGroupName(String email, String groupName) {

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                                    new CustomException(ErrorCode.USER_NOT_FOUND));

        List<Bookmark> bookmarkList = bookmarkRepository.findAllByUserIdAndGroupName(user.getId(), groupName);

        return BookmarkDto.of(bookmarkList);
    }

    @Override
    public BookmarkDto addBookmark(String email, Long recipeId, String groupName) {

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND));

        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() ->
                new CustomException(ErrorCode.RECIPE_NOT_FOUNT));

        Optional<Bookmark> optionalBookmark =
                            bookmarkRepository.findByUserIdAndRecipeId(user.getId(), recipeId);

        if (optionalBookmark.isPresent()) {
            throw new CustomException(ErrorCode.ALREADY_REGISTERED_RECIPE);
        }

        Bookmark bookmark = Bookmark.builder()
                .groupName(groupName)
                .userId(user.getId())
                .recipe(recipe)
                .build();
        bookmarkRepository.save(bookmark);

        return BookmarkDto.of(bookmark);
    }

    @Override
    public BookmarkDto editBookmark(String email, BookmarkDto bookmarkDto) {

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND));

        Bookmark bookmark = bookmarkRepository
                                    .findById(bookmarkDto.getId()).orElseThrow(() ->
                                    new CustomException(ErrorCode.RECIPE_NOT_FOUNT));

        if (!user.getId().equals(bookmark.getUserId())) {
            throw new CustomException(ErrorCode.UNMATCHED_USER_RECIPE);
        }

        bookmark.setGroupName(bookmarkDto.getGroupName());
        bookmarkRepository.save(bookmark);

        return BookmarkDto.of(bookmark);
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
