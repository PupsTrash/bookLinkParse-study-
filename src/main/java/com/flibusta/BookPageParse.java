package com.flibusta;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookPageParse {

    private final InitialSearch initialSearch;

    public String getBookInfo(String request) throws IOException {
        Element primaryBlock = null;
        primaryBlock = getPrimaryBlock(request);
        final String titleBook = primaryBlock.childNode(getNumNodeToStart(primaryBlock) + 1).toString();
        final String sizeBook = primaryBlock.childNode(getNumNodeToStart(primaryBlock) + 2).childNode(0).toString();
        return titleBook + " " + sizeBook;
    }


    public Map<String, String> getBookFormat(String request) throws IOException {
        Element primaryBlock = getPrimaryBlock(request);
        Integer numNodeToStart = getNumNodeToStart(primaryBlock);
        Map<String, String> mapFormatAndIdBook = primaryBlock.childNodes().stream().parallel()
                .skip(numNodeToStart + 5)
                .filter(o -> o.hasAttr("href"))
                .collect(Collectors.toMap(
                        k -> k.childNode(0).toString(),
                        v -> v.attr("href")));

        if (mapFormatAndIdBook.isEmpty()) {
            return primaryBlock.childNodes().stream().parallel()
                    .skip(numNodeToStart)
                    .filter(o -> o.hasAttr("href"))
                    .collect(Collectors.toMap(
                            k -> k.childNode(0).toString(),
                            v -> v.attr("href")));
        }
        return mapFormatAndIdBook;
    }


    private Integer getNumNodeToStart(Element primaryBlock) {
        return primaryBlock.childNodes().stream()
                .filter(k -> k.attributes().hasKey("src")).findFirst()
                .get().siblingIndex();
    }

    private Element getPrimaryBlock(String request) throws IOException, NullPointerException {

        Document page = initialSearch.getPage("b/" + request);
        String namePrimaryBlock = page.select("span[style=size]").first().parentNode().attr("class");
        return page.select("div[class=" + namePrimaryBlock + "]").first();

    }
}
