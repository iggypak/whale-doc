package whale.crawlers;

import org.springframework.stereotype.Component;
import whale.dto.LinkDTO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FileLinkCrawler extends LinkCrawler{

    public FileLinkCrawler(LinkDTO resource){
        super(resource);
    }

    public FileLinkCrawler(LinkDTO resource, String pattern) {
        super(resource, pattern);
    }

    public FileLinkCrawler(String filepath) {
        super(LinkDTO.of(filepath));
    }

    @Override
    String getContent() throws IOException {
        String result = Files
                .readAllLines(Path.of(resourceAddress.url()))
                .stream()
                .collect(Collectors.joining());
        return result;
    }

    @Override
    public Collection<LinkDTO> fetchLinks() {
        try {
            return Files.readAllLines(Path.of(resourceAddress.url()))
                    .stream()
                    .map(
            e -> LinkDTO.of(e, "file", true)
                    )
                    .filter(this::isValidPath)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    Collection<LinkDTO> findLinksByPattern() {
        return List.of();
    }

    @Override
    public boolean isValidPath(LinkDTO path) {
        if (Files.exists(Path.of(path.url())) && Files.isRegularFile(Path.of(path.url()))) {
            return true;
        }
        return false;
    }

}
