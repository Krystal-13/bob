package com.zerobase.bob.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuName {

    @Id
    String name;
    public MenuName(String name) {
        this.name = name;
    }
}
