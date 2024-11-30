package whale.crawlers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FileLinkCrawler extends LinkCrawler<String>{

    public FileLinkCrawler(String resource){
        super(resource);
    }

    public FileLinkCrawler(String resource, String pattern) {
        super(resource, pattern);
    }

    @Override
    String getContent() throws IOException {
        String result = Files.readAllLines(
                Path.of(resourceAddress)
        )
                .stream()
                .collect(Collectors.joining());
        return result;
    }

    @Override
    public Collection<String> fetchLinks() {
        try {
            return Files.readAllLines(Path.of(resourceAddress))
                    .stream()
                    .map(
                            e -> findFirstMatch(e).orElseGet(String::new)
                    )
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    Collection<String> findLinksByPattern() {
        return List.of();
    }

    @Override
    public boolean isValidPath(String path) {
        if (Files.exists(Path.of(path)) && Files.isRegularFile(Path.of(path))) {
            return true;
        }
        return false;
    }

}
