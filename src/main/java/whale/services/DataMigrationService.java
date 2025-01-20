package whale.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import whale.entity.Link;

import java.util.List;

@Service
public class DataMigrationService {

    private final ClickHouseStorageService clickHouseStorageService;
    private final RedisStorageService redisStorageService;

    @Autowired
    public DataMigrationService(ClickHouseStorageService clickHouseStorageService, RedisStorageService redisStorageService) {
        this.clickHouseStorageService = clickHouseStorageService;
        this.redisStorageService = redisStorageService;
    }

    public void migrateDataToClickHouse() {
        List<Link> allLinks = redisStorageService.getAllLinks();
        clickHouseStorageService.saveLinkBatch(allLinks);
    }
}
