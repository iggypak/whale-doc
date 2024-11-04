package whale.crawlers;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class HttpLinkCrawler extends LinkCrawler<URL> {

    private final Set<String> uniqueLinks = new HashSet<>();
    private final Set<String> mistakenLinks = new HashSet<>();

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
    public Collection<String> getLinks() {
        return getLinkTree(getLinks(resource.toString()));
    }

    private Collection<String> getLinkTree(Collection <String> links) {
        for (String link: links) {
            if(link.startsWith(resource.toString()) && !uniqueLinks.contains(link) && !link.equals(resource.toString())){
                uniqueLinks.add(link);
                uniqueLinks.addAll(getLinkTree(getLinks(link)));
            }
        }
        return uniqueLinks;
    }

    private Collection <String> getLinks(String httpLink) {
        try {
            if (!isValidPath(httpLink)){
                return Set.of(httpLink);
            }
            Document dom = Jsoup
                    .connect(httpLink)
                    .timeout(2000)
                    .get();
            Elements elements = dom.select("a");
            return elements
                    .stream()
                    .filter(element -> element.hasAttr("href"))
                    .map(element -> getValidPath(
                                httpLink, element.attr("href")
                            )
                    )
                    .toList();
        } catch (HttpStatusException e) {
            mistakenLinks.add(httpLink);
            return Set.of();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    Collection<String> findLinksByPattern() {
        return List.of();
    }

    private boolean isValidPath(String httpLink) {
        URL url = null;
        try {
            url = new URL(httpLink);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return isValidPath(url);
    }

    @Override
    boolean isValidPath(URL path) {
        try {
            String contentType = path.openConnection().getContentType();
            if(contentType.matches(".*(text|application/xml|application/*+xml).*"))
                return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    public String getValidPath(String httpUrl, String path) {
        String result = "";
        if (path.equals("/")) {
            return httpUrl;
        }
        if (path.startsWith("/")){
            result = resource.toString().concat(path);
            return result;
        }
        return path;
    }
}
