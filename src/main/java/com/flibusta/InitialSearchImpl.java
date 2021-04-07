package com.flibusta;

import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

@Service
@NoArgsConstructor
public class InitialSearchImpl implements InitialSearch {

    public Document getPage(String searchRequest) throws IOException {
        String sourceUrl = "http://flibustahezeous3.onion/booksearch?ask=" + searchRequest;
        Document page = Jsoup.connect(sourceUrl)
                .proxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 9150)))
                .userAgent("Chrome/4.0.249.0 Safari/532.5")
                .referrer("http://www.google.com")
                .get();
        return page;
    }

}
