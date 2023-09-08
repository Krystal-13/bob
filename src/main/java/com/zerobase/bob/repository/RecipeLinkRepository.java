package com.zerobase.bob.repository;

import com.zerobase.bob.entity.RecipeLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeLinkRepository extends JpaRepository<RecipeLink, Long>, RecipeLinkCustomRepository {
    RecipeLink findFirstByOrderByIdDesc();
    List<RecipeLink> findByNameLike(String name);
}
