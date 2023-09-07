package com.zerobase.bob;

import com.zerobase.bob.entity.Recipe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class RecipeTest {

    @Test
    @DisplayName("레시피 엔티티")
    void Builder_test() {

        List<String> ingredients = List.of("rice");
        List<String> steps = List.of("steaming");

        Recipe recipe = Recipe.builder()
                .name("aaa")
                .description("description")
                .ingredients(ingredients)
                .steps(steps)
                .cookTime("10분")
                .build();

        assertEquals("aaa", recipe.getName());
        assertEquals("rice", recipe.getIngredients().get(0));
        assertEquals(Collections.EMPTY_LIST, recipe.getReviews());

    }
}
