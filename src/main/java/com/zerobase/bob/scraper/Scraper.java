package com.zerobase.bob.scraper;

import com.zerobase.bob.entity.Recipe;
import com.zerobase.bob.entity.RecipeLink;
import com.zerobase.bob.entity.type.RecipeType;
import com.zerobase.bob.exception.CustomException;
import com.zerobase.bob.exception.ErrorCode;
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
    private static final String LIST_URL = "https://www.10000recipe.com/recipe/list.html?q=%s&page=%d";
    private static final String BASE_URL = "https://www.10000recipe.com";

    public List<RecipeLink> scrapRecipeUrlAndName(String menuName) {

        List<RecipeLink> list = new ArrayList<>();
        int page = 1;

        try {
            String url = String.format(LIST_URL, menuName, page);
            Connection connection = Jsoup.connect(url);
            Document document = connection.get();
            String text = Objects.requireNonNull(document.getElementsByTag("b").first()).text();
            int maxPage = Integer.parseInt(text.replaceAll(",","")) / 40 + 1;

            while (maxPage >= page) {

                url = String.format(LIST_URL, menuName, page);
                connection = Jsoup.connect(url);
                document = connection.get();

                Elements parsingDivs = document.getElementsByClass("common_sp_list_li");

                for (Element e : parsingDivs) {
                    String attr = e.getElementsByAttribute("href").attr("href");
                    String recipeName =
                            e.getElementsByClass("common_sp_caption_tit line2").text();

                    list.add(new RecipeLink(BASE_URL + attr, recipeName, RecipeType.RECIPE_10000));
                }
                page++;
            }

        } catch (IOException e) {
            log.debug("scrap failed : " + menuName + " - " + page + " page");
        }

        return list;
    }

    public Recipe scrapRecipe(RecipeLink recipeLink) {

        List<String> stepList = new ArrayList<>();
        List<String> ingredientList = new ArrayList<>();

        try {
            Connection connection = Jsoup.connect(recipeLink.getLink());
            Document document = connection.get();

            String image = Objects.requireNonNull(document.getElementById("main_thumbs"))
                                                                .attr("src");

            String time = document.getElementsByClass("view2_summary_info2").text();

            Element element = document.getElementById("divConfirmedMaterialArea");
            Elements tag = Objects.requireNonNull(element).getElementsByTag("li");
            for (Element e : tag) {
                String text = e.child(0).text();
                String unit = e.select(".ingre_unit").text(); // 계량

                ingredientList.add(text + " " + unit);
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
                    .ingredients(ingredientList)
                    .steps(stepList)
                    .cookTime(time)
                    .link(recipeLink.getLink())
                    .build();

        } catch (IOException e) {
            log.debug("scrap failed : " + recipeLink.getLink());
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }
    }
}
