package com.flibusta;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class InitialParse {
    @Autowired
    private InitialSearch initialSearch;

    //переработать так чтобы был один запрос на getPage

    public InitialParse() throws IOException {
    }

    public Map<String, String> getLinkMap() throws IOException {
        Document page = initialSearch.getPage("булгаков");
        Elements listElements = page.select("h3").next("ul").first().select("li");
        return listElements.stream().parallel().collect(Collectors.toMap(
                k -> k.text(),
                v -> new StringBuffer(v.child(0).attributes().toString().split("\"")[1])
                        .insert(0, "http://flibustahezeous3.onion").toString())); //ключ - название, значение - ссылка

    }

    public void getTitleLinkMap() throws IOException {
        Document page = initialSearch.getPage("булгаков");
        System.out.println(page.select("h3").first().text());
    }
}
