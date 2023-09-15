package com.zerobase.bob.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @OneToOne
    private Recipe recipe;
    private String groupName;
    @Lob
    private String memo;

    @Builder
    public Bookmark(Long userId, Recipe recipe, String groupName, String memo) {
        this.userId = userId;
        this.recipe = recipe;
        this.groupName = groupName;
        this.memo = memo;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }

}
