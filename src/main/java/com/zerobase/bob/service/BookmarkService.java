package com.zerobase.bob.service;

import com.zerobase.bob.dto.BookmarkDto;
import com.zerobase.bob.entity.Bookmark;
import com.zerobase.bob.entity.Recipe;
import com.zerobase.bob.entity.User;
import com.zerobase.bob.exception.CustomException;
import com.zerobase.bob.exception.ErrorCode;
import com.zerobase.bob.repository.BookmarkRepository;
import com.zerobase.bob.repository.RecipeRepository;
import com.zerobase.bob.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;


    public List<BookmarkDto> getBookmarkListByGroupName(String email, String groupName) {

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                                    new CustomException(ErrorCode.USER_NOT_FOUND));

        List<Bookmark> bookmarkList = bookmarkRepository.findAllByUserIdAndGroupName(user.getId(), groupName);

        return BookmarkDto.of(bookmarkList);
    }


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


    public BookmarkDto bookmarkMemo(String email, Long bookmarkId, String memo) {

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND));

        Bookmark bookmark = bookmarkRepository
                .findById(bookmarkId).orElseThrow(() ->
                        new CustomException(ErrorCode.RECIPE_NOT_FOUNT));

        if (!user.getId().equals(bookmark.getUserId())) {
            throw new CustomException(ErrorCode.UNMATCHED_USER_RECIPE);
        }

        bookmark.setMemo(memo);
        bookmarkRepository.save(bookmark);

        return BookmarkDto.of(bookmark);
    }


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


    public Boolean deleteBookmark(String email, Long bookmarkId) {

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                                new CustomException(ErrorCode.USER_NOT_FOUND));

        Bookmark bookmark = bookmarkRepository.findById(bookmarkId).orElseThrow(() ->
                                                    new CustomException(ErrorCode.RECIPE_NOT_FOUNT));

        if (!user.getId().equals(bookmark.getUserId())) {
            throw new CustomException(ErrorCode.UNMATCHED_USER_RECIPE);
        }

        bookmarkRepository.delete(bookmark);

        return true;
    }
}
