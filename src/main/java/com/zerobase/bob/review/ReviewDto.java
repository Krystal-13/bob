package com.zerobase.bob.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private Long recipeId;
    private String userName;
    private String text;
    private String image;
    private int score;
    private LocalDateTime registeredAt;

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
