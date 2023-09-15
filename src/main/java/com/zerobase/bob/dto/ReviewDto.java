package com.zerobase.bob.dto;

import com.zerobase.bob.entity.Review;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewDto {

    private Long id;
    private Long recipeId;
    private String userName;
    private String text;
    private String image;
    private int score;
    private LocalDateTime registeredAt;

    @Builder
    public ReviewDto(Long id, Long recipeId, String userName, String text, String image, int score, LocalDateTime registeredAt) {
        this.id = id;
        this.recipeId = recipeId;
        this.userName = userName;
        this.text = text;
        this.image = image;
        this.score = score;
        this.registeredAt = registeredAt;
    }

    public static ReviewDto of(Review review) {
        return ReviewDto.builder()
                .id(review.getId())
                .recipeId(review.getRecipeId())
                .userName(review.getUser().getName())
                .text(review.getText())
                .image(review.getImage())
                .score(review.getScore())
                .registeredAt(review.getRegisteredAt())
                .build();
    }
}
