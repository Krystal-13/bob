package com.zerobase.bob.review;

import com.zerobase.bob.entity.Recipe;
import com.zerobase.bob.entity.User;
import com.zerobase.bob.exception.CustomException;
import com.zerobase.bob.exception.ErrorCode;
import com.zerobase.bob.filesave.FileSave;
import com.zerobase.bob.repository.RecipeRepository;
import com.zerobase.bob.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    public ReviewDto writeReview(String email, ReviewDto reviewDto, MultipartFile file) {

        String urlFilename = "";

        if (file != null) {
            urlFilename = FileSave.getNewSaveFile(file);
        }

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND));

        Recipe recipe = recipeRepository.findById(reviewDto.getRecipeId()).orElseThrow(() ->
                new CustomException(ErrorCode.RECIPE_NOT_FOUNT));

        Review review = Review.builder()
                .recipeId(recipe.getId())
                .user(user)
                .text(reviewDto.getText())
                .score(reviewDto.getScore())
                .image(urlFilename)
                .build();
        reviewRepository.save(review);

        return ReviewDto.of(review);
    }
}
