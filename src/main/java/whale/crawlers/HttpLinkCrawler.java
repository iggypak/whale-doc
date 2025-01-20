package whale.crawlers;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import whale.entity.Link;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class HttpLinkCrawler extends LinkCrawler {
    private static final String INCORRECT_MIMETYPE_MESSAGE = "not text mime-type";
    private static final int CONNECTION_TIMEOUT = 2000;

    public HttpLinkCrawler(String url) throws MalformedURLException {
        super(url);
    }

    @Override
    public String getContent() throws IOException {
        return new URL(resourceAddress.getUrl()).getContent().toString();
    }

    @Override
    public Collection<Link> fetchLinks() {
        return buildLinkTree(fetchLinks(resourceAddress.getUrl()));
    }

    private void printMistakenLinks() {
        linkStorageService.getAllLinks().forEach((url) -> {
            System.err.println(url + " -> " + url.getComment());
        });
    }

    private Collection<Link> buildLinkTree(Collection<Link> links) {
        for (Link link : links) {
            if (isLinkEligibleForProcessing(link)) {
                if (isValidPath(link)) {
                    link.setValidUrl(true);
                    linkStorageService.saveLink(link);
                    linkStorageService.saveLinkBatch(new HashSet<>(buildLinkTree(fetchLinks(link.getUrl()))));
                } else {
                    link.setValidUrl(false);
                    link.setComment(INCORRECT_MIMETYPE_MESSAGE);
                    linkStorageService.saveLink(link);
                }
            }
        }
        return linkStorageService.getAllLinks();
    }

    private boolean isLinkEligibleForProcessing(Link link) {
        return link.getUrl().startsWith(resourceAddress.getUrl()) && !linkStorageService.existsById(link.getUrl()) && !link.equals(resourceAddress.getUrl());
    }

    private Collection<Link> fetchLinks(String httpLink) {
        try {
            Document dom = Jsoup.connect(httpLink)
                    .timeout(CONNECTION_TIMEOUT)
                    .get();
            Elements elements = dom.select("a");
            return elements
                    .stream()
                    .filter(element -> element.hasAttr("href"))
                    .map(element -> resolvePath(httpLink, element.attr("href")))
                    .map(link -> Link.of(link, resourceAddress.getUrl()))
                    .collect(Collectors.toSet());
        } catch (HttpStatusException e) {
            linkStorageService.saveLink(buildErrorLink(httpLink, e.getMessage()));
            return List.of();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    Collection<Link> findLinksByPattern() {
        return List.of();
    }

    private boolean isValidPath(String httpLink) {
        return isValidPath(new Link(httpLink));
    }

    @Override
    boolean isValidPath(Link path) {
        try {
            String contentType = new URL(path.getUrl()).openConnection().getContentType();
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
            return resourceAddress.getUrl().concat(path);
        }
        return path;
    }

    private Link buildErrorLink(String link, String errorMsg) {
        Link errorLink = new Link();
        errorLink.setValidUrl(false);
        errorLink.setUrl(link);
        errorLink.setComment(errorMsg);
        return mapToDto(errorLink);
    }
}
