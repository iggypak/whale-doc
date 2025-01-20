package whale.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import whale.entity.Link;

import java.util.Collection;
import java.util.List;

@Service
public class CompositeStorageService implements LinkStorageService{

    @Autowired
    private RedisStorageService redisStorageService;
    @Autowired
    private ClickHouseStorageService clickHouseStorageService;

    @Override
    public void saveLink(Link link) {
        redisStorageService.saveLink(link);
        clickHouseStorageService.saveLink(link);
    }

    @Override
    public void saveLinkBatch(Collection<Link> links) {
        redisStorageService.saveLinkBatch(links);
        clickHouseStorageService.saveLinkBatch(links);
    }

    @Override
    public List<Link> getAllLinks() {
        return clickHouseStorageService.getAllLinks();
    }

    @Override
    public List<Link> getByResource(String resource) {
        return clickHouseStorageService.getByResource(resource);
    }

    @Override
    public boolean existsById(String id) {
        return redisStorageService.existsById(id) && clickHouseStorageService.existsById(id);
    }
}
