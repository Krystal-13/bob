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

    /**
     1:N 조회는 JPA 의 N+1 조회 문제를 야기시킵니다. Fetch Join 등으로 해결 할 수 있고,
     N+1 은 면접에서 자주 등장하는 문제이니 여러 문서를 바탕으로 꼭 정리하기
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Lob
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
