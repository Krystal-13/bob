package com.zerobase.bob.repository;

import com.zerobase.bob.entity.RecipeLink;

import java.util.List;

public interface RecipeLinkCustomRepository {
    void saveAll(List<RecipeLink> list);
}
