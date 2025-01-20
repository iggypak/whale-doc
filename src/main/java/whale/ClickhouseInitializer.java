package whale;

import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ClickhouseInitializer {

    private final JdbcTemplate jdbcTemplate;

    public ClickhouseInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // TODO: fix table definition after testing
    @PostConstruct
    public void createTable() {
        String createTableQuery = """
                CREATE TABLE IF NOT EXISTS link (
                                  url String,
                                  validUrl Nullable(UInt8),
                                  resource Nullable(String),
                                  comment Nullable(String)
                              ) ENGINE = MergeTree()
                              ORDER BY (url);
                """;

        jdbcTemplate.execute(createTableQuery);
    }
}
