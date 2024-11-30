package whale.crawlers;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract public class LinkCrawler <T>{
    public static final String HTTP_LINK_PATTERN = "(https|http)://[a-zA-Z_.:0-9-]+(/[?a-zA-Z_.0-9-]+)*\\?{0,1}[a-zA-Z=&,;:0-9#]*";
    protected T resourceAddress;
    protected Pattern pattern;

    public LinkCrawler(T resourceAddress) {
        this(resourceAddress, HTTP_LINK_PATTERN);
    }

    public LinkCrawler(T resourceAddress, String pattern){
        if(!isValidPath(resourceAddress)) {
            throw new IllegalArgumentException("Invalid resourse");
        }
        this.resourceAddress = resourceAddress;
        this.pattern = Pattern.compile(pattern);
    }

    abstract String getContent() throws IOException;
    public abstract Collection<String> fetchLinks();
    abstract Collection<String> findLinksByPattern();
    abstract boolean isValidPath(T path);

    public Optional<String> findFirstMatch(String text) {
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return Optional.ofNullable(matcher.group());
        }
        return Optional.ofNullable(null);
    }



}
