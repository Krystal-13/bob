package com.zerobase.bob.repository;

import com.zerobase.bob.entity.RecipeDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeSearchRepository extends ElasticsearchRepository<RecipeDocument, String> {

    Page<RecipeDocument> findByIngredientsContains(String ingredients, Pageable pageable);
}
