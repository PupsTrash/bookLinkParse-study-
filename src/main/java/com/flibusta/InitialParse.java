package com.flibusta;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InitialParse {

    private final InitialSearch initialSearch;

    private Document page;

    public Map<String, String> getLinkMap(String searchRequest) throws IOException {
        this.page = initialSearch.getPage("booksearch?ask=" + searchRequest);
        Element p = page.select("h3:contains(Найденные книги)").first();

        if (p == null) {
            return Map.of();
        }
        Elements list = page.select("h3:contains(Найденные книги)").next("ul").first().select("li");

        return list.stream().parallel().collect(Collectors.toMap(
                k -> new StringBuffer(k.child(0).attributes().toString()
                        .split("\"")[1].replace("/b/", ""))
                        .toString(),
                v -> v.text()));
    }

    public String getTitleLinkMap() {
        if (page == null) {
            return "error get Title List";
        }
        return page.select("h3:contains(Найденные книги)").first().text();
    }

    public List<String> getPageButton() {
        Element test = page.select("ul[class=pager]").first();
        if (test == null || test.childNodes().size() < 1) {
            return List.of();
        }
        final List<String> collect = test.childNodes().stream()
                .filter(c -> c.hasAttr("class"))
                .filter(o -> o.childNode(0).childNodes().size() > 0)
                .filter(i -> i.childNode(0).childNode(0).toString().length() < 3)
                .map(c -> c.childNode(0).childNode(0).toString())
                .collect(Collectors.toList());
        return collect;
    }
}
