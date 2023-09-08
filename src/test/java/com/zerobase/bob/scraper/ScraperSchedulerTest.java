package com.zerobase.bob.scraper;

import com.zerobase.bob.entity.MenuName;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScraperSchedulerTest {

    @Test
    void scrapRecipeNameTop100() {
        String TOP_100_URL = "https://www.10000recipe.com/ranking/home_new.html?rtype=k&dtype=d";

        List<MenuName> top100List = new ArrayList<>();

        try {

            Connection connection = Jsoup.connect(TOP_100_URL);
            Document document = connection.get();
            Elements elements = document.getElementsByClass("best_cont");

            for (Element element : elements) {
                String text = element.getElementsByTag("a").text();
                top100List.add(new MenuName(text));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals("두부조림", top100List.get(0).getName());
    }
}