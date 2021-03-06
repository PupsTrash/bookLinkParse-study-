package com.flibusta;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ProxySelector;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InitialParseImpl {

    private final InitialSearch initialSearch;

    private Document page;

    //переработать так чтобы был один запрос на getPage


    public Map<String, String> getLinkMap(String searchRequest) throws IOException {
        this.page = initialSearch.getPage(searchRequest);
        Element p = page.select("h3:contains(Найденные книги)").first();
        if (p == null){
            return null;
        }
        Elements list = page.select("h3:contains(Найденные книги)").next("ul").first().select("li");

        return list.stream().parallel().collect(Collectors.toMap(
                k ->new StringBuffer(k.child(0).attributes().toString().split("\"")[1])
                        .insert(0, "http://flibustahezeous3.onion").toString(),
                v -> v.text()));


        /*Elements listElements = page.select("h3").next("ul").first().select("li");
        return listElements.stream().parallel().collect(Collectors.toMap(
                k -> k.text(),
                v -> new StringBuffer(v.child(0).attributes().toString().split("\"")[1])
                        .insert(0, "http://flibustahezeous3.onion").toString())); //ключ - название, значение - ссылка*/
    }

    public String getTitleLinkMap() throws IOException {
        if (page == null){
            return "";
        }
        return page.select("h3:contains(Найденные книги)").first().text();
    }
}
