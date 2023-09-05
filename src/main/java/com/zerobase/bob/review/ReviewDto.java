package com.zerobase.bob.review;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewDto {

    private Long recipeId;
    private String userName;
    private String text;
    private String image;
    private int score;
    private LocalDateTime registeredAt;

    @Builder
    public ReviewDto(Long recipeId, String userName, String text, String image, int score, LocalDateTime registeredAt) {
        this.recipeId = recipeId;
        this.userName = userName;
        this.text = text;
        this.image = image;
        this.score = score;
        this.registeredAt = registeredAt;
    }

    public static ReviewDto of(Review review) {
        return ReviewDto.builder()
                .recipeId(review.getRecipeId())
                .userName(review.getUser().getName())
                .text(review.getText())
                .image(review.getImage())
                .score(review.getScore())
                .registeredAt(review.getRegisteredAt())
                .build();
    }
}
