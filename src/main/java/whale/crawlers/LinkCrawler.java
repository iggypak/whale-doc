package whale.crawlers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract public class LinkCrawler <T>{
    public static final String HTTP_LINK_PATTERN = "(https|http)://[a-zA-Z_.:0-9-]+(/[?a-zA-Z_.0-9-]+)*\\?{0,1}[a-zA-Z=&,;:0-9#]*";
    protected T resource;
    protected Pattern pattern;

    public LinkCrawler(T resource) {
        this(resource, HTTP_LINK_PATTERN);
    }

    public LinkCrawler(T resource, String pattern){
        if(!isValidPath(resource)) {
            throw new IllegalArgumentException("Invalid resourse");
        }
        this.resource = resource;
        this.pattern = Pattern.compile(pattern);
    }

    abstract String getContent() throws IOException;
    public abstract List<String> getLinks();
    abstract List<String> findLinksByPattern();
    abstract boolean isValidPath(T path);

    public Optional<String> findFirstMatch(String text) {
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return Optional.ofNullable(matcher.group());
        }
        return Optional.ofNullable(null);
    }



}
