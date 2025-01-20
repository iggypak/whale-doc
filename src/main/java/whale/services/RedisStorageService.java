package whale.services;

import org.springframework.stereotype.Service;
import whale.entity.Link;
import whale.repositories.LinkCacheRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class RedisStorageService implements LinkStorageService {

    private final LinkCacheRepository linkCacheRepository;

    public RedisStorageService(LinkCacheRepository linkCacheRepository) {
        this.linkCacheRepository = linkCacheRepository;
    }

    @Override
    public void saveLink(Link link) {
        if (!existsById(link.getUrl())) {
            linkCacheRepository.save(link);
        }
    }

    @Override
    public void saveLinkBatch(Collection<Link> links) {
        links.stream().forEach(this::saveLink);
    }

    @Override
    public List<Link> getAllLinks() {
        var iterator = linkCacheRepository.findAll().iterator();
        List<Link> links = Collections.emptyList();
        while (iterator.hasNext()){
            links.add(iterator.next());
        }
        return links;
    }

    @Override
    public List<Link> getByResource(String resource) {
        return List.of();
    }

    @Override
    public boolean existsById(String url) {
        return linkCacheRepository.existsById(url);
    }
}
