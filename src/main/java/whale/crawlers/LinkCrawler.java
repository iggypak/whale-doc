package whale.crawlers;

import org.springframework.stereotype.Component;
import whale.dto.LinkDTO;
import whale.entity.Link;

import java.io.IOException;
import java.util.Collection;
import java.util.regex.Pattern;

@Component
abstract public class LinkCrawler {
    public static final String HTTP_LINK_PATTERN = "(https|http)://[a-zA-Z_.:0-9-]+(/[?a-zA-Z_.0-9-]+)*\\?{0,1}[a-zA-Z=&,;:0-9#]*";
    protected LinkDTO resourceAddress;
    protected Pattern pattern;

    public LinkCrawler(LinkDTO resourceAddress) {
        this(resourceAddress, HTTP_LINK_PATTERN);
    }

    public LinkCrawler(LinkDTO resourceAddress, String pattern){
        setResourceAddress(resourceAddress);
        this.pattern = Pattern.compile(pattern);
    }

    abstract String getContent() throws IOException;
    public abstract Collection<LinkDTO> fetchLinks();
    abstract Collection<LinkDTO> findLinksByPattern();
    abstract boolean isValidPath(LinkDTO path);

    public LinkCrawler setResourceAddress(LinkDTO resourceAddress) {
        if(!isValidPath(resourceAddress)) {
            throw new IllegalArgumentException("Invalid resourse");
        }
        this.resourceAddress = resourceAddress;
        return this;
    }

    public static LinkDTO mapToDto(Link link) {
        return new LinkDTO(link.getId(), link.getUrl(), link.getValidUrl(), link.getType(), link.getMetadata());
    }

    public static Link mapToEntity(LinkDTO linkDTO){
        var link = new Link();
        link.setId(linkDTO.id());
        link.setUrl(linkDTO.url());
        link.setType(linkDTO.type());
        link.setValidUrl(linkDTO.validUrl());
        link.setMetadata(linkDTO.metadata());
        return link;
    }
}
