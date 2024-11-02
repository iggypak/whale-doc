package whale.crawlers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class HttpLinkCrawler extends LinkCrawler<URL> {

    public HttpLinkCrawler(String url) throws MalformedURLException {
        super(new URL(url));
    }

    public HttpLinkCrawler(URL inetAddress) {
        super(inetAddress);
    }

    @Override
    String getContent() throws IOException {
        return resource.getContent().toString();
    }

    @Override
    public List<String> getLinks() {
        try {
            Document dom = Jsoup.connect(resource.toString()).get();
            Elements elements = dom.select("a");
            return elements
                    .stream()
                    .filter(element -> element.hasAttr("href"))
                    .map(element -> element.attr("href"))
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    List<String> findLinksByPattern() {
        return List.of();
    }

    @Override
    boolean isValidPath(URL path) {
        return true;
    }


}
