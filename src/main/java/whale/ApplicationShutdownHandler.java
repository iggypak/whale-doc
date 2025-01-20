package whale;

import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import whale.services.DataMigrationService;

@Component
public class ApplicationShutdownHandler {
    private final DataMigrationService dataMigrationService;
    private final boolean saveToClickHouse;

    @Autowired
    public ApplicationShutdownHandler(DataMigrationService dataMigrationService,
                                      @Value("${default.save-to-clickhouse:true}") boolean saveToClickHouse) {
        this.dataMigrationService = dataMigrationService;
        this.saveToClickHouse = saveToClickHouse;
    }

    @PreDestroy
    public void onShutdown() {
        if (saveToClickHouse) {
            dataMigrationService.migrateDataToClickHouse();
        }
    }
}
