package whale.crawlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import whale.entity.Link;
import whale.services.LinkStorageService;

import java.io.IOException;
import java.util.Collection;
import java.util.regex.Pattern;

@Component
abstract public class LinkCrawler {
    public static final String HTTP_LINK_PATTERN = "(https|http)://[a-zA-Z_.:0-9-]+(/[?a-zA-Z_.0-9-]+)*\\?{0,1}[a-zA-Z=&,;:0-9#]*";
    protected Link resourceAddress;
    protected Pattern pattern;
    @Autowired
    protected LinkStorageService linkStorageService;

    public LinkCrawler(Link resourceAddress) {
        this(resourceAddress, HTTP_LINK_PATTERN);
    }

    public LinkCrawler(String resourceUrl) {
        this(new Link(resourceUrl));
    }

    public LinkCrawler(Link resourceAddress, String pattern){
        setResourceAddress(resourceAddress);
        this.pattern = Pattern.compile(pattern);
    }

    abstract String getContent() throws IOException;
    public abstract Collection<Link> fetchLinks();
    abstract Collection<Link> findLinksByPattern();
    abstract boolean isValidPath(Link path);

    public LinkCrawler setResourceAddress(Link resourceAddress) {
        if(!isValidPath(resourceAddress)) {
            throw new IllegalArgumentException("Invalid resourse");
        }
        this.resourceAddress = resourceAddress;
        return this;
    }

    public static Link mapToDto(Link link) {
        return new Link(link.getUrl(), link.isValidUrl(), link.getResource(), null);
    }

}
