import com.flibusta.InitialParse;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@ComponentScan("com.flibusta")
@Configuration
public class Check {
/*    public static void main(String[] args) throws IOException {
    Document page = getPage();
        Element topResponse = page.select("h3").first();
        Elements listElements = page.select("h3").next("ul").first().select("li");


        for (Element a : listElements
             ) {
            String hrefFromMainSerch = a.child(0).attributes().toString().split("\"")[1];
            System.out.println(a.text() + " .....  " + "http://flibustahezeous3.onion" + hrefFromMainSerch);
        }

    }
    private Document getPage(String searchRequest) throws IOException {
        String sourceUrl = "http://flibustahezeous3.onion/booksearch?ask=";

        Document page = Jsoup.connect(sourceUrl)
                .proxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 9150)))
                .userAgent("Chrome/4.0.249.0 Safari/532.5")
                .referrer("http://www.google.com")
                .get();
        return page;
    }*/
public static void main(String[] args) {
    ApplicationContext ctx = new AnnotationConfigApplicationContext(Check.class);
    InitialParse initialParse = ctx.getBean(InitialParse.class);
    try {
        System.out.println(initialParse.getLinkMap().toString());
        initialParse.getTitleLinkMap();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
