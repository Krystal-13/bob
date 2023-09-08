package com.zerobase.bob.scraper;

import com.zerobase.bob.entity.MenuName;
import com.zerobase.bob.entity.RecipeLink;
import com.zerobase.bob.repository.MenuNameRepository;
import com.zerobase.bob.repository.RecipeLinkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScraperScheduler {

    private final Scraper scraper;
    private final RecipeLinkRepository recipeLinkRepository;
    private final MenuNameRepository menuNameRepository;
    private static final String TOP_100_URL = "https://www.10000recipe.com/ranking/home_new.html?rtype=k&dtype=d";

    public void scrapRecipeNameTop100() {

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
            log.debug("scrapTop100 failed");
        }

        menuNameRepository.saveAll(top100List);
    }

    @Scheduled(cron = "${scheduler.scrap.10000Recipe}")
    public void scrapRecipeScheduling() {

        scrapRecipeNameTop100();
        List<MenuName> top100List = menuNameRepository.findAll();

        for (MenuName recipeName : top100List) {
            log.info("scraping scheduler is started -> " + recipeName);
            List<RecipeLink> recipeLinks = scraper.scrapRecipeUrlAndName(recipeName.getName());

            recipeLinkRepository.saveAll(recipeLinks);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }

}
