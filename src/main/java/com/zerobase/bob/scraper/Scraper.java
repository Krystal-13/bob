package com.zerobase.bob.scraper;

import com.zerobase.bob.entity.Recipe;
import com.zerobase.bob.entity.RecipeLink;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Component
@Slf4j
public class Scraper {
    private static final String listUrl = "https://www.10000recipe.com/recipe/list.html?q=";
    private static final String baseUrl = "https://www.10000recipe.com";

    public List<RecipeLink> scrapRecipeUrlAndName(String menuName) {

        List<RecipeLink> list = new ArrayList<>();

        try {
            Connection connection = Jsoup.connect(listUrl + menuName);
            Document document = connection.get();

            Elements parsingDivs = document.getElementsByClass("common_sp_list_li");

        /*
        검색한 리스트 총 40개 레시피 url
        /recipe/*******  -> recipeUrl + attr = 메뉴 링크
         */
            int count = 0; // 한페이지당 40개

            for (Element e : parsingDivs) {
                String attr = e.getElementsByAttribute("href").attr("href");
                String recipeName = e.getElementsByClass("common_sp_caption_tit line2").text();

                list.add(new RecipeLink(baseUrl + attr, recipeName));
            }

        } catch (IOException e) {
            log.debug("scrap failed : " + menuName);
        }

        return list;
    }

    public Recipe scrapRecipe(RecipeLink recipeLink, Long userId) {

        List<String> stepList = new ArrayList<>();
        List<String> ingredients = new ArrayList<>();

        try {
            Connection connection = Jsoup.connect(recipeLink.getLink());
            Document document = connection.get();

            String image = Objects.requireNonNull(document.getElementById("main_thumbs")).attr("src");

            String time = document.getElementsByClass("view2_summary_info2").text();

            Element element = document.getElementById("divConfirmedMaterialArea");
            Elements tag = Objects.requireNonNull(element).getElementsByTag("li");
            for (Element e : tag) {
                String text = e.child(0).text();
                String unit = e.select(".ingre_unit").text(); // 계량

                ingredients.add(text + " " + unit);
            }

            Element steps = document.getElementsByClass("view_step").get(0);
            Elements step = steps.getElementsByClass("media-body");
            for (Element e : step) {
                stepList.add(e.text());
            }

            return Recipe
                    .builder()
                    .name(recipeLink.getName())
                    .image(image)
                    .description("")
                    .ingredients(ingredients)
                    .steps(stepList)
                    .cookTime(time)
                    .source(recipeLink.getLink())
                    .userId(userId)
                    .build();

        } catch (IOException e) {
            log.debug("scrap failed : " + recipeLink.getLink());
            return null;
            // TODO
        }
    }
}
