package com.flibusta;

import org.jsoup.nodes.Document;

import java.io.IOException;

public interface InitialSearch {
    Document getPage(String searchRequest) throws IOException;


}
