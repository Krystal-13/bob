package com.zerobase.bob.repository;

import com.zerobase.bob.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findAllByUserIdAndSourceContainingIgnoreCase(Long userId, String Source);

    boolean existsByUserIdAndSource(Long userId, String source);

}
