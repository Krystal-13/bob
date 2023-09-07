package com.zerobase.bob.repository;

import com.zerobase.bob.entity.Recipe;
import com.zerobase.bob.entity.RecipeLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeLinkRepository extends JpaRepository<RecipeLink, Long> {

    boolean existsByLink(String link);
    Optional<RecipeLink> findByLink(String link);

    RecipeLink findFirstByOrderByIdDesc();

}
