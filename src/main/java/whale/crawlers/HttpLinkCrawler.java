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
    private static final String INCORRECT_MIMETYPE_MESSAGE = "not text mime-type";
    private static final int CONNECTION_TIMEOUT = 2000;
    private final Set<String> uniqueLinks = new HashSet<>();
    private final Map<String, String> mistakenLinks = new HashMap<>();

    public HttpLinkCrawler(String url) throws MalformedURLException {
        super(new URL(url));
    }

    public HttpLinkCrawler(URL inetAddress) {
        super(inetAddress);
    }

    @Override
    String getContent() throws IOException {
        return resourceAddress.getContent().toString();
    }

    @Override
    public Collection<String> fetchLinks() {
        Collection<String> result = buildLinkTree(fetchLinks(resourceAddress.toString()));
        result.add(resourceAddress.toString());
        printMistakenLinks();
        return result;
    }

    private void printMistakenLinks() {
        mistakenLinks.forEach((url, message) -> {
            System.err.println(url + ": " + message);
        });
    }

    private Collection<String> buildLinkTree(Collection<String> links) {
        for (String link : links) {
            if (isLinkEligibleForProcessing(link)) {
                if (isValidPath(link)) {
                    uniqueLinks.add(link);
                    uniqueLinks.addAll(buildLinkTree(fetchLinks(link)));
                } else {
                    mistakenLinks.put(link, INCORRECT_MIMETYPE_MESSAGE);
                }
            }
        }
        return uniqueLinks;
    }

    private boolean isLinkEligibleForProcessing(String link) {
        return link.startsWith(resourceAddress.toString()) && !uniqueLinks.contains(link) && !link.equals(resourceAddress.toString());
    }

    private Collection<String> fetchLinks(String httpLink) {
        try {
            Document dom = Jsoup.connect(httpLink)
                    .timeout(CONNECTION_TIMEOUT)
                    .get();
            Elements elements = dom.select("a");
            return elements.stream()
                    .filter(element -> element.hasAttr("href"))
                    .map(element -> resolvePath(httpLink, element.attr("href")))
                    .toList();
        } catch (HttpStatusException e) {
            mistakenLinks.put(httpLink, e.getMessage());
            return List.of();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    Collection<String> findLinksByPattern() {
        return List.of();
    }

    private boolean isValidPath(String httpLink) {
        try {
            URL url = new URL(httpLink);
            return isValidPath(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    boolean isValidPath(URL path) {
        try {
            String contentType = path.openConnection().getContentType();
            return contentType != null && contentType.matches(".*(text|application/xml|application/*+xml).*");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String resolvePath(String baseUrl, String path) {
        if (path.equals("/")) {
            return baseUrl;
        }
        if (path.startsWith("/")) {
            return resourceAddress.toString().concat(path);
        }
        return path;
    }
}
