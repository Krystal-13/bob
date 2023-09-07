package com.zerobase.bob.repository;

import com.zerobase.bob.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Optional<Recipe> findByRecipeLinkId(Long recipeLinkId);

    List<Recipe> findAllByUserId(Long userId);
}
