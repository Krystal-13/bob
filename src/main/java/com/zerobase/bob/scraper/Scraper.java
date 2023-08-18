package com.zerobase.bob.scraper;

import com.zerobase.bob.dto.RecipeDto;
import com.zerobase.bob.entity.Ingredient;
import com.zerobase.bob.entity.MenuLink;
import com.zerobase.bob.repository.MenuLinkRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@RequiredArgsConstructor
public class Scraper {

  private static final String listUrl = "https://www.10000recipe.com/recipe/list.html?q=";

  private static final String baseUrl = "https://www.10000recipe.com";

  private final MenuLinkRepository menuLinkRepository;


  public List<MenuLink> scrapMenuUrlAndName(String menuName) {

    List<MenuLink> list = new ArrayList<MenuLink>();
    try {
      Connection connection = Jsoup.connect(listUrl + menuName);
      Document document = connection.get();

      Elements parsingDivs = document.getElementsByClass("common_sp_list_li");

        /*
        검색한 리스트 총 40개 레시피 url
        /recipe/*******  -> recipeUrl + attr = 메뉴 링크
         */
      String baseUrl = "https://www.10000recipe.com";
      int count = 0; // 한페이지당 40개

      for (Element e : parsingDivs) {
        String attr = e.getElementsByAttribute("href").attr("href");
        String text = e.getElementsByClass("common_sp_caption_tit line2").text();

        list.add(new MenuLink(attr, text));
        System.out.println(count + " :: " + attr + " :: " + text);
      }

      menuLinkRepository.saveAll(list);

    } catch (IOException e) {
      e.printStackTrace();
    }

    return list;
  }

  public RecipeDto scrapRecipe(Long menuLinkId) throws IOException {

    MenuLink menuLink = menuLinkRepository.findById(menuLinkId)
        .orElseThrow(RuntimeException::new);

    List<Ingredient> list = new ArrayList<>();
    List<String> stepList = new ArrayList<>();

    Connection connection = Jsoup.connect(baseUrl + menuLink.getLink());
    Document document = connection.get();

    String time = document.getElementsByClass("view2_summary_info2").text();
    Element element = document.getElementById("divConfirmedMaterialArea");

    Elements tag = Objects.requireNonNull(element).getElementsByTag("li");

    for (Element e : tag) {
      String text = e.child(0).text();
      String unit = e.select(".ingre_unit").text(); // 계량

      list.add(new Ingredient(text, unit));

      System.out.println(text + " :: " + unit);
    }

    Element steps = document.getElementsByClass("view_step").get(0);
    Elements step = steps.getElementsByClass("media-body");

    for (Element e : step) {
      stepList.add(e.text());
    }

    return RecipeDto.builder()
        .name(menuLink.getName())
        .description(null)
        .ingredients(list)
        .steps(stepList)
        .cookTime(time)
        .build();
  }
}
