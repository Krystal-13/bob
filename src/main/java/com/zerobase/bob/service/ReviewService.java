package com.zerobase.bob.service;

import com.zerobase.bob.dto.ReviewDto;
import com.zerobase.bob.entity.Recipe;
import com.zerobase.bob.entity.Review;
import com.zerobase.bob.entity.User;
import com.zerobase.bob.exception.CustomException;
import com.zerobase.bob.exception.ErrorCode;
import com.zerobase.bob.repository.RecipeRepository;
import com.zerobase.bob.repository.ReviewRepository;
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
    private final AwsS3Service awsS3Service;

    public ReviewDto writeReview(String email, ReviewDto reviewDto, MultipartFile file) {

        String urlFilename = awsS3Service.uploadAndGetUrl(file);

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

        recipe.setReviews(review);
        recipeRepository.save(recipe);

        return ReviewDto.of(review);
    }
}
