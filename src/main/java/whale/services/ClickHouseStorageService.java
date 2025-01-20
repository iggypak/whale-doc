package whale.services;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import whale.entity.Link;

import java.util.Collection;
import java.util.List;

@Service
public class ClickHouseStorageService implements LinkStorageService {
    private final static String insertSql = "INSERT INTO link (url, validUrl, resource, comment) VALUES('%s', %d, '%s', '%s')";
    private final static String selectAllSql = "SELECT * FROM link";
    private final static String selectByResource = "SELECT * FROM link WHERE resource = ?";
    private final static String existsSelect = "SELECT * FROM link WHERE url = ?";

    private final JdbcTemplate jdbcTemplate;

    public ClickHouseStorageService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveLink(Link link) {
        if (!existsById(link.getUrl())) {
            jdbcTemplate.execute(insertSql.formatted(link.getUrl(), link.isValidUrl() ? 1 : 0, link.getResource(), link.getComment()));
        }
    }

    @Override
    public void saveLinkBatch(Collection<Link> links) {
        links.forEach(this::saveLink);
    }

    @Override
    public List<Link> getAllLinks() {
        return jdbcTemplate.query(selectAllSql, new BeanPropertyRowMapper<>(Link.class));
    }

    @Override
    public List<Link> getByResource(String resource) {
        return jdbcTemplate.query(selectByResource, new BeanPropertyRowMapper<>(Link.class));
    }

    @Override
    public boolean existsById(String id) {
        return !jdbcTemplate.query(existsSelect, ps -> {
            ps.setString(1, id);
        }, (rs, rowNum) -> new Link(
                rs.getString(1),
                rs.getBoolean(2),
                rs.getString(3),
                rs.getString(4)
        )).isEmpty();
    }
}
