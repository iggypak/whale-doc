package whale.crawlers;

import org.springframework.stereotype.Component;
import whale.entity.Link;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FileLinkCrawler extends LinkCrawler{

    public FileLinkCrawler(Link resource){
        super(resource);
    }

    public FileLinkCrawler(Link resource, String pattern) {
        super(resource, pattern);
    }

    public FileLinkCrawler(String filepath) {
        super(filepath);
    }

    @Override
    String getContent() throws IOException {
        String result = Files
                .readAllLines(Path.of(resourceAddress.getUrl()))
                .stream()
                .collect(Collectors.joining());
        return result;
    }

    @Override
    public Collection<Link> fetchLinks() {
        try {
            return Files.readAllLines(Path.of(resourceAddress.getUrl()))
                    .stream()
                    .map(
            e -> Link.of(e, resourceAddress.getUrl())
                    )
                    .filter(this::isValidPath)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    Collection<Link> findLinksByPattern() {
        return List.of();
    }

    @Override
    public boolean isValidPath(Link path) {
        if (Files.exists(Path.of(path.getUrl())) && Files.isRegularFile(Path.of(path.getUrl()))) {
            return true;
        }
        return false;
    }

}
