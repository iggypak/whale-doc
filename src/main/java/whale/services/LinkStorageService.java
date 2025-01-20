package whale.services;

import whale.entity.Link;

import java.util.Collection;
import java.util.List;

public interface LinkStorageService {
    void saveLink(Link link);
    void saveLinkBatch(Collection<Link> links);
    List<Link> getAllLinks();
    List<Link> getByResource(String resource);
    boolean existsById(String id);
}
