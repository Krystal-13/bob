package com.zerobase.bob.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = {AuditingEntityListener.class})
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recipe_id")
    private Long recipeId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String text;
    private String image;
    private int score;

    @CreatedDate
    private LocalDateTime registeredAt;

    @Builder
    public Review(Long recipeId, User user, String text, String image, int score) {
        this.recipeId = recipeId;
        this.user = user;
        this.text = text;
        this.image = image;
        this.score = score;
        this.registeredAt = getRegisteredAt();
    }
}
